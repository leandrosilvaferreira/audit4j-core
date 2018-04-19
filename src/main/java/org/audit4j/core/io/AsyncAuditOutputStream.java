/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core.io;

import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.LifeCycleContext;
import org.audit4j.core.RunStatus;
import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;

import reactor.core.Environment;
import reactor.core.composable.Deferred;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;
import reactor.function.support.Boundary;

/**
 * The Class AsyncAuditOutputStream.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 *
 * @since 2.0.0
 */
public class AsyncAuditOutputStream implements AuditOutputStream<AuditEvent> {

	/** The output stream. */
	AuditOutputStream<AuditEvent> outputStream;

	/** The annotation stream. */
	AuditOutputStream<AnnotationAuditEvent> annotationStream;

	/** The deferred. */
	Deferred<AuditEvent, Stream<AuditEvent>> deferred = null;

	/** The annotation deferred. */
	Deferred<AnnotationAuditEvent, Stream<AnnotationAuditEvent>> annotationDeferred = null;

	/** The Constant ENV. */
	static Environment ENV;

	/** The b. */
	Boundary b = null;

	/** The b anno. */
	Boundary bAnno = null;

	/**
	 * Instantiates a new async audit output stream.
	 *
	 * @param outputStream
	 *            the output stream
	 * @param annotationStream
	 *            the annotation stream
	 */
	public AsyncAuditOutputStream(final AuditOutputStream<AuditEvent> outputStream, final AuditOutputStream<AnnotationAuditEvent> annotationStream) {
		this.outputStream = outputStream;
		this.annotationStream = annotationStream;

		ENV = new Environment();
		this.b = new Boundary();
		this.deferred = Streams.<AuditEvent>defer().env(ENV).dispatcher(Environment.RING_BUFFER).get();
		final Stream<AuditEvent> stream = this.deferred.compose();
		stream.consume(this.b.bind(event -> outputStream.write(event)));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent )
	 */
	/**
	 * {@inheritDoc}
	 *
	 * @see org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent)
	 *
	 */
	@Override
	public AsyncAuditOutputStream write(final AuditEvent event) {

		if (event instanceof AnnotationAuditEvent) {
			this.annotationStream.write((AnnotationAuditEvent) event);
		}
		else {
			this.deferred.accept(event);
			this.b.await();
		}

		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.audit4j.core.io.AuditOutputStream#close()
	 */
	/**
	 * {@inheritDoc}
	 *
	 * @see org.audit4j.core.io.AuditOutputStream#close()
	 *
	 */
	@Override
	public void close() {

		ENV.shutdown();
		if (this.outputStream != null) {
			this.outputStream.close();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#clone()
	 *
	 */
	@Override
	public Object clone() {

		return null;
	}

	/**
	 * Find last AuditEvents by Actor
	 *
	 * @param actor
	 *            the actor
	 * @param limit
	 *            limit of registers
	 * @param repository
	 *            repository
	 * @return List of audit events
	 */
	@Override
	public List<AuditEvent> findAuditEventsByActor(final String actor, final Integer limit, final String repository) {

		if (LifeCycleContext.getInstance().getStatus().equals(RunStatus.RUNNING)) {
			return this.outputStream.findAuditEventsByActor(actor, limit, repository);
		}
		return new ArrayList<>();

	}

}
