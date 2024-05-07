INSERT INTO Channel (id, name) VALUES (1, 'Test Channel 1');
INSERT INTO Channel (id, name) VALUES (2, 'Test Channel 2');
INSERT INTO Channel (id, name) VALUES (3, 'Test Channel 3');

INSERT INTO CustomAssetDashboard(id, assetId, assetTitle, category, channelId, createDate) VALUES ('1', '1', '1', 'default', 1, timestamp '2021-05-14');
INSERT INTO CustomAssetDashboard(id, assetId, assetTitle, category, channelId, createDate) VALUES ('2', '1', '1', 'default', 2, timestamp '2021-05-15');
INSERT INTO CustomAssetDashboard(id, assetId, assetTitle, category, channelId, createDate) VALUES ('3', '1', '1', 'default', 3, timestamp '2021-05-16');

INSERT INTO Experiment(id, channelId, createDate, name) VALUES (1, 1, timestamp '2021-05-14', 'Experiment 1');
INSERT INTO Experiment(id, channelId, createDate, name) VALUES (2, 2, timestamp '2021-05-15', 'Experiment 1');
INSERT INTO Experiment(id, channelId, createDate, name) VALUES (3, 3, timestamp '2021-05-16', 'Experiment 3');

INSERT INTO ExperimentMetric(id, experimentId) VALUES (1, 1);

INSERT INTO ExperimentVariant(id, experimentId) VALUES (1, 1);

INSERT INTO ExperimentVariantMetric(id, experimentMetricId) VALUES (1, 1);

INSERT INTO Segment (id, channelId, createDate, name) VALUES (1, 1, timestamp '2021-05-14', 'Segment 1');
INSERT INTO Segment (id, channelId, createDate, name) VALUES (2, 2, timestamp '2021-05-15', 'Segment 2');
INSERT INTO Segment (id, channelId, createDate, name) VALUES (3, 3, timestamp '2021-05-16', 'Segment 3');