
package org.audit4j.core;

import java.lang.reflect.Method;
import java.util.List;

import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;

import lombok.NonNull;

/**
 * IAuditManager. This interface describes actions available for AuditManager implementations.
 *
 * @since 2.4.1
 */
public interface IAuditManager {

	/**
	 * Audit.
	 *
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	boolean audit(AuditEvent event);

	/**
	 * Audit with annotation.
	 *
	 * @param clazz
	 *            the clazz
	 * @param method
	 *            the method
	 * @param args
	 *            the args
	 * @return true, if successful
	 *
	 */
	boolean audit(Class<?> clazz, Method method, Object[] args);

	/**
	 * Audit.
	 *
	 * @param annotationEvent
	 *            the annotation event
	 * @return true, if successful
	 */
	boolean audit(AnnotationAuditEvent annotationEvent);

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
	public abstract List<AuditEvent> findAuditEventsByActor(@NonNull final String actor, final Integer limit, @NonNull String repository);

	// /**
	// * Enable.
	// */
	// void enable();
	//
	// /**
	// * Disable.
	// */
	// void disable();
	//
	// /**
	// * Shutdown.
	// */
	// void shutdown();
}
