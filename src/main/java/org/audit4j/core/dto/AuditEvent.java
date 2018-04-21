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

package org.audit4j.core.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

/**
 * The Class AuditEvent.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 *
 * @since 1.0.0
 */
public class AuditEvent extends Event {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7009763987501353992L;

	/** The actor. */
	private String actor;

	/** The origin. */
	private String origin;

	/** The action. */
	private String action;

	/** The action item. */
	private List<Field> fields = new ArrayList<>();

	/** The tag. */
	private String tag;

	/** The repository. */
	private String repository;

	/**
	 * Instantiates a new audit event.
	 */
	public AuditEvent() {

	}

	/**
	 * Instantiates a new audit event.
	 *
	 * @param actor
	 *            the actor
	 * @param action
	 *            the action
	 * @param fields
	 *            the fields
	 */
	public AuditEvent(final String actor, final String action, final Field... fields) {
		this.actor = actor;
		this.action = action;
		for (final Field field : fields) {
			this.addField(field);
		}
	}

	/**
	 * Instantiates a new audit event.
	 *
	 * @param actor
	 *            the actor
	 * @param action
	 *            the action
	 * @param origin
	 *            the origin
	 * @param fields
	 *            the fields
	 */
	public AuditEvent(final String actor, final String action, final String origin, final Field... fields) {
		this.actor = actor;
		this.action = action;
		this.origin = origin;
		for (final Field field : fields) {
			this.addField(field);
		}
	}

	/**
	 * Gets the actor.
	 *
	 * @return the actor
	 */
	public String getActor() {

		return this.actor;
	}

	/**
	 * Sets the actor.
	 *
	 * @param actor
	 *            the new actor
	 */
	public void setActor(final String actor) {

		this.actor = actor;
	}

	/**
	 * Gets the origin.
	 *
	 * @return the origin
	 */
	public String getOrigin() {

		return this.origin;
	}

	/**
	 * Sets the origin.
	 *
	 * @param origin
	 *            the new origin
	 */
	public void setOrigin(final String origin) {

		this.origin = origin;
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public String getAction() {

		return this.action;
	}

	/**
	 * Gets the action as AuditAction
	 *
	 * @return Audit Action including action, color and icon
	 */
	public AuditAction getAuditAction() {

		if (StringUtils.isBlank(this.action)) {
			return null;
		}

		return new Gson().fromJson(this.action, AuditAction.class);
	}

	/**
	 * Gets the origin as AuditOrigin
	 *
	 * @return Audit Origin including ip, hostname, city, etc
	 */
	public AuditOrigin getAuditOrigin() {

		if (StringUtils.isBlank(this.origin)) {
			return null;
		}

		try {
			return new Gson().fromJson(this.origin, AuditOrigin.class);
		}
		catch (final Exception e) {
			return AuditOrigin.builder().ip(this.origin).build();
		}

	}

	/**
	 * Sets the action.
	 *
	 * @param action
	 *            the new action
	 */
	public void setAction(final String action) {

		this.action = action;
	}

	/**
	 * Adds the element.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param type
	 *            the type
	 */
	public void addField(final String name, final Object value, final Object type) {

		if (value == null) {
			this.fields.add(new Field(name, null, null));
		}
		else {
			this.fields.add(new Field(name, value.toString(), type.toString()));
		}
	}

	/**
	 * Adds the field.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void addField(final String name, final Object value) {

		if (value == null) {
			this.fields.add(new Field(name, null, null));
		}
		else {
			this.fields.add(new Field(name, value.toString(), value.getClass().getName()));
		}
	}

	/**
	 * Adds the element.
	 *
	 * @param field
	 *            the field
	 */
	public void addField(final Field field) {

		this.fields.add(field);
	}

	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	public List<Field> getFields() {

		return this.fields;
	}

	/**
	 * Sets the fields.
	 *
	 * @param fields
	 *            the new fields
	 */
	public void setFields(final List<Field> fields) {

		this.fields = fields;
	}

	/**
	 * Gets the tag.
	 *
	 * @return the tag
	 */
	public String getTag() {

		return this.tag;
	}

	/**
	 * Sets the tag.
	 *
	 * @param tag
	 *            the new tag
	 */
	public void setTag(final String tag) {

		this.tag = tag;
	}

	/**
	 * Gets the repository.
	 *
	 * @return the repository
	 */
	public String getRepository() {

		return this.repository;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.audit4j.core.dto.Event#setRepository(java.lang.String)
	 *
	 */
	@Override
	public void setRepository(final String repository) {

		this.repository = repository;
	}
}
