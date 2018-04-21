
package org.audit4j.core.dto;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class AuditOrigin implements Serializable {

	private static final long serialVersionUID = -3693000370180892363L;

	@NonNull
	@SerializedName("ip")
	@Expose
	public String ip;

	@SerializedName("hostname")
	@Expose
	public String hostname;

	@SerializedName("type")
	@Expose
	public String type;

	@SerializedName("continent_code")
	@Expose
	public String continentCode;

	@SerializedName("continent_name")
	@Expose
	public String continentName;

	@SerializedName("country_code")
	@Expose
	public String countryCode;

	@SerializedName("country_name")
	@Expose
	public String countryName;

	@SerializedName("region_code")
	@Expose
	public String regionCode;

	@SerializedName("region_name")
	@Expose
	public String regionName;

	@SerializedName("city")
	@Expose
	public String city;

	@SerializedName("zip")
	@Expose
	public String zip;

	@SerializedName("latitude")
	@Expose
	public Double latitude;

	@SerializedName("longitude")
	@Expose
	public Double longitude;

}
