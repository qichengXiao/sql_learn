package com.cscw.web.protocol.parser;

import com.google.gson.JsonObject;

public class BasicResultPaser extends JsonUnitData {

	@Override
	public JsonObject parser() {
		return new JsonObject();
	}

}
