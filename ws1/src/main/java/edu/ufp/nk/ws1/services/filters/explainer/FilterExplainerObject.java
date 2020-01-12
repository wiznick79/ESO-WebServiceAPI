package edu.ufp.nk.ws1.services.filters.explainer;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Data
public class FilterExplainerObject {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String degree;
	private String explainerName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate day;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime start;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime end;

	public FilterExplainerObject(Map<String, String> data){

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
			this.logger.error(e.getMessage());
		}

		this.explainerName = data.get("name");
		this.degree = data.get("degree");
	}
}
