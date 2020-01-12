package edu.ufp.nk.ws1.services.filters.Explainer;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Data
public class FilterExplainerObject {
	private String degree;
	private String explainerName;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate day;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime start;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime end;

	public FilterExplainerObject(Map<String, String> data){
		LocalDate day = null;
		LocalTime end = null, start = null;

		String dayString = data.get("day");
		String endString = data.get("start");
		String startString = data.get("end");

		// Empty Days
		try{
			if(dayString != null)
				day = LocalDate.parse(dayString);

			if(startString != null)
				start = LocalTime.parse(startString);

			if(endString != null)
				end = LocalTime.parse(endString);

		}catch (Exception e) {
			// TODO: LOG MAYBE NIKOS?
			System.out.println("\n\n ERROR DAY / END / START \n" + e.getMessage());
		}

		this.explainerName = data.get("name");
		this.degree = data.get("degree");
		this.start = start;
		this.end = end;
		this.day = day;
	}
}
