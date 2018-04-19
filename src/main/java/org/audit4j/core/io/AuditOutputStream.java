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

import java.util.List;

import org.audit4j.core.dto.AuditEvent;

/**
 * The Interface AuditOutputStream.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 *
 * @since 2.0.0
 */
public interface AuditOutputStream<T extends AuditEvent> {

	/**
	 * Write.
	 *
	 * @param event
	 *            the event
	 * @return the audit output stream
	 */
	AuditOutputStream<T> write(T event);

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
	List<T> findAuditEventsByActor(String actor, Integer limit, String repository);

	/**
	 * Close.
	 */
	void close();

	/**
	 * Clone.
	 *
	 * @return the object
	 */
	Object clone();
}
