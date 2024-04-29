CREATE TEMP FUNCTION mapEventProperties(eventProperties STRING)
RETURNS STRING
LANGUAGE js AS """
	var arrayString = '[';
	var json = JSON.parse(eventProperties);
	for (var key in json) {
		arrayString = arrayString + '{"name": "' + key +'", "value": "' + json[key] + '"},';
	}
	arrayString = arrayString.substr(0, arrayString.length-1) + ']'
	return arrayString;
""";
UPDATE BQEvent SET properties = ARRAY(
		SELECT STRUCT(
			JSON_VALUE(property, "$.name") AS name,
			JSON_VALUE(property, "$.value") AS  value
		)
		FROM UNNEST(JSON_EXTRACT_ARRAY(mapEventProperties(eventProperties))) AS property
	)
WHERE 1=1