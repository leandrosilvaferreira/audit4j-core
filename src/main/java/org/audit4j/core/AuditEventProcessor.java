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

package org.audit4j.core;

import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.HandlerException;
import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.util.Log;

/**
 * This class is used to process audit events. Processing includes, formatting, validating and execute handlers.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 *
 * @since 1.0
 */
public class AuditEventProcessor {

	/** The conf. */
	private ConcurrentConfigurationContext configContext;

	/**
	 * Process the audit event.
	 *
	 * @param event
	 *            the event
	 */
	public void process(final AuditEvent event) {

		boolean execute = true;
		if (!this.configContext.getFilters().isEmpty()) {
			for (final AuditEventFilter filter : this.configContext.getFilters()) {
				if (!filter.accepts(event)) {
					execute = false;
					break;
				}
			}
		}
		if (execute) {
			this.executeHandlers(event);
		}
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
	public List<AuditEvent> findAuditEventsByActor(final String actor, final Integer limit, final String repository) {

		for (final Handler handler : this.configContext.getHandlers()) {
			try {
				if (handler.implementsSearch()) {
					return handler.findAuditEventsByActor(actor, limit, repository);
				}
			}
			catch (final HandlerException e) {
				Log.warn("Failed to find audit events.", e);
			}
		}

		return new ArrayList<>();

	}

	/**
	 * Execute handlers.
	 *
	 * @param event
	 *            the event
	 */
	void executeHandlers(final AuditEvent event) {

		final String formattedEvent = this.configContext.getLayout().format(event);
		for (final Handler handler : this.configContext.getHandlers()) {
			handler.setAuditEvent(event);
			handler.setQuery(formattedEvent);
			try {
				handler.handle();
			}
			catch (final HandlerException e) {
				Log.warn("Failed to submit audit event.", e);
			}
		}
	}

	/**
	 * Sets the config context.
	 *
	 * @param configContext
	 *            the new config context
	 */
	public void setConfigContext(final ConcurrentConfigurationContext configContext) {

		this.configContext = configContext;
	}

}
