INSERT INTO Channel (id, name) VALUES (1, 'Test Channel 1');

INSERT INTO CustomAssetDashboard(id, assetId, assetTitle, category, channelId, createDate) VALUES ('1', '1', '1', 'default', 1, timestamp '${now-3d}');

INSERT INTO Experiment(id, channelId, createDate, name) VALUES (1, 1, timestamp '${now-1d}', 'Experiment 1');

INSERT INTO ExperimentMetric(id, experimentId) VALUES (1, 1);

INSERT INTO ExperimentVariant(id, experimentId) VALUES (1, 1);

INSERT INTO ExperimentVariantMetric(id, experimentMetricId) VALUES (1, 1);

INSERT INTO Segment (id, channelId, createDate, name) VALUES (1, 1, timestamp '${now-2d}', 'Segment 1');