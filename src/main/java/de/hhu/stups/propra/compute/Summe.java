package de.hhu.stups.propra.compute;

import lombok.Data;

public @Data class Summe {
	private Long summand1;
	private Long summand2;

	public Long getSum() {
		return summand1 + summand2;
	}
}
