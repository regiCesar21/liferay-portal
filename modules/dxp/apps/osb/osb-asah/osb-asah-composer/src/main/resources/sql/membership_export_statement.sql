EXPORT DATA
	OPTIONS (
		field_delimiter = ';',
		format = 'CSV',
		header = false,
		overwrite = true,
		uri = 'gs://{{dag.default_args['google_project_id']}}-data-replica/{{dag.default_args['ac_project_id']}}/individual-segment/{{ts}}/*.csv'
	)
AS (
	SELECT
		Membership.createDate, Membership.identityId, Membership.channelId, Membership.individualId,
		Membership.modifiedDate, Membership.segmentId, Membership.status
	FROM
		`{{ dag.default_args['ac_project_id'] }}.membership` Membership
	INNER JOIN
		`{{dag.default_args['ac_project_id']}}.identity` Identity
	ON
		Membership.identityId = Identity.id
	INNER JOIN
		`{{dag.default_args['ac_project_id']}}.individual` Individual
	ON
		Identity.individualId = Individual.id
);