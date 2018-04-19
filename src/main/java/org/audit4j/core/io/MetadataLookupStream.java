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
import org.audit4j.core.MetadataHandler;
import org.audit4j.core.RunStatus;
import org.audit4j.core.dto.AuditEvent;

/**
 * The Class MetadataLookupStream.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 *
 * @since 2.4.0
 */
public class MetadataLookupStream implements AuditOutputStream<AuditEvent> {

	/** The handler. */
	MetadataHandler handler;

	/** The output stream. */
	AuditOutputStream<AuditEvent> outputStream;

	/**
	 * Instantiates a new metadata lookup stream.
	 *
	 * @param outputStream
	 *            the output stream
	 */
	public MetadataLookupStream(final AuditOutputStream<AuditEvent> outputStream) {
		this.outputStream = outputStream;
		this.handler = new MetadataHandler();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent)
	 *
	 */
	@Override
	public AuditOutputStream<AuditEvent> write(final AuditEvent event) {

		this.outputStream.write(this.handler.enhanceFromMetadata(event));
		return this.outputStream;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.audit4j.core.io.AuditOutputStream#close()
	 *
	 */
	@Override
	public void close() {

		if (this.outputStream != null) {
			this.outputStream.close();
			this.outputStream = null;
		}
	}

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
