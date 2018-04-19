
package org.audit4j.core.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditAction implements Serializable {

	private static final long serialVersionUID = 6108506043122409973L;

	@NonNull
	String type;

	@NonNull
	String icon;

	@NonNull
	String color;

	@NonNull
	String action;
}
