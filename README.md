# poc-iot-schemadesign

A little PoC project based on a CSV file with metric data, on how MongoDB schema design impacts file storage and access patterns.

Prerequisites:

- Java 1.8
- Maven
- MongoDB

<!-- TOC -->

- [poc-iot-schemadesign](#poc-iot-schemadesign)
  - [Schema discussion](#schema-discussion)
    - [Default schema details](#default-schema-details)
    - [Arrayed Schema details](#arrayed-schema-details)
  - [Comparison: arrayed and non-arrayed fields](#comparison-arrayed-and-non-arrayed-fields)
  - [Typical queries](#typical-queries)

<!-- /TOC -->

## Schema discussion

**Default**: Every row is stored as a document. Every column is stored as a field.

**Arrayed**: Every row is stored as a document. _Column values that belong together are stored together_.

**Bucketed**: Multiple rows are grouped in the same document based on a range. Individual row data is still stored and optimized using arrayed schema design.

Benefits of the latter:

- Indexes consume space and increase in size as the amount of documents grow. The number of indexes will most likely grow as well based on querying needs. By limiting the number of documents, storing the IoT data will be more cost efficient
- Scaling will be more easy
- No loss in functionality nor data

### Default schema details

    {
        "_id" : ObjectId("5b89347bd8110a1a9addea84"),
        "Cycle" : NumberLong("3049663787"),
        "Degrees" : NumberLong(732569299),
        "Channel" : 120.498,
        "Session" : 2,
        "Input pressure" : 3,
        "FMV pressure" : 6.33,
        "DGNS degrees" : 2.33,
        "DGNS pressure" : 29.003,
        "Arrival time" : -2.5,
        "FD1 detection time" : 0.0527,
        "FD1 detection degrees" : 0.0527,
        "FD2 detection time" : 239.992,
        "FD2 detection degrees" : 0.0807,
        "LW detection degrees" : 331.25601,
        "# valid windings" : 236.07401,
        "#1 Windingtime" : 8,
        "#1 Windingtime Timestamp" : 0.0073,
        "#2 Windingtime" : 0.0078,
        "#2 Windingtime Timestamp" : 0.0133,
        "#3 Windingtime" : 0.0138,
        "#3 Windingtime Timestamp" : 0.0193,
        "#4 Windingtime" : 0.0198,
        "#4 Windingtime Timestamp" : 0.0252,
        "#5 Windingtime" : 0.0258,
        "#5 Windingtime Timestamp" : 0.0311,
        "#6 Windingtime" : 0.0317,
        "#6 Windingtime Timestamp" : 0.0371,
        "#7 Windingtime" : 0.0377,
        "#7 Windingtime Timestamp" : 0.0436,
        "#8 Windingtime" : 0.0441,
        "#8 Windingtime Timestamp" : 0.0511,
        "#9 Windingtime" : 0.0516,
        "#9 Windingtime Timestamp" : 0,
        "#10 Windingtime" : 0,
        "#10 Windingtime Timestamp" : 0,
        "#11 Windingtime" : 0,
        "#11 Windingtime Timestamp" : 0,
        "#12 Windingtime" : 0,
        "#12 Windingtime Timestamp" : 0,
        "#13 Windingtime" : 0,
        "#13 Windingtime Timestamp" : 0,
        "#14 Windingtime" : 0,
        "#14 Windingtime Timestamp" : 0,
        "#15 Windingtime" : 0,
        "#15 Windingtime Timestamp" : 0,
        "#16 Windingtime" : 0,
        "#16 Windingtime Timestamp" : 0,
        "PFT BrakeDegrees" : 0,
        "PFT BrakeSeconds" : 213.222,
        "BurstPosition" : 0.04491,
        "ExpTPBurstToPStart" : 29.003,
        "TPBurstToPStart" : 0.011,
        "Degrees Start Insertion" : 0.01225,
        "ExpTPStartToPaExpected" : 61.083,
        "TPStartToPaExpected" : 0.05747,
        "Px Position" : 0.0559,
        "Direction change count" : 299.9,
        "Reverse count" : 34
    }

### Arrayed Schema details

    {
        "_id" : ObjectId("5b894086d8110a1e09afed99"),
        "Cycle" : NumberLong("3049663787"),
        "Degrees" : NumberLong(732569299),
        "Channel" : 120.498,
        "Session" : 2,
        "Input pressure" : 3,
        "FMV pressure" : 6.33,
        "DGNS degrees" : 2.33,
        "DGNS pressure" : 29.003,
        "Arrival time" : -2.5,
        "FD detection times" : [
            0.0527,
            239.992
        ],
        "FD detection degrees" : [
            0.0527,
            0.0807
        ],
        "LW detection degrees" : 331.25601,
        "# valid windings" : 236.07401,
        "windingtimes" : [
            8,
            0.0078,
            0.0138,
            0.0198,
            0.0258,
            0.0317,
            0.0377,
            0.0441,
            0.0516,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        ],
        "windingtimestamps" : [
            0.0073,
            0.0133,
            0.0193,
            0.0252,
            0.0311,
            0.0371,
            0.0436,
            0.0511,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        ],
        "PFT BrakeDegrees" : 0,
        "PFT BrakeSeconds" : 213.222,
        "BurstPosition" : 0.04491,
        "ExpTPBurstToPStart" : 29.003,
        "TPBurstToPStart" : 0.011,
        "Degrees Start Insertion" : 0.01225,
        "ExpTPStartToPaExpected" : 61.083,
        "TPStartToPaExpected" : 0.05747,
        "Px Position" : 0.0559,
        "Direction change count" : 299.9,
        "Reverse count" : 34
    }

## Comparison: arrayed and non-arrayed fields

| #   | Scenario      | Default     | Arrayed     | Comment                                 |
| --- | ------------- | ----------- | ----------- | --------------------------------------- |
| 1   | "count"       | 4009        | 4009        | nr of inserted documents                |
| 2   | "size"        | 6.426.427 B | 4.189.405 B | -35% size in memory (count\*avgObjSize) |
| 3   | "avgObjSize"  | 1.603 B     | 1.045 B     | -35% document size                      |
| 4   | "storageSize" | 4096        | 4096        | Same block size reserved                |

More information on [mongodb docs](https://docs.mongodb.com/manual/reference/command/collStats/#collstats-output)

## Typical queries

Find all documents having a FD detecting time lower than or equal to 0.05:

    db.rec.find({"$or" : [{"FD1 detection time" : {"$lte" : 0.05}},{"FD2 detection time" : {"$lte" : 0.05}} ] })
        73

    db.rec2.find({"FD detection times" : {"$elemMatch" : {"$lte" : 0.05}}} ).count()
        73

Find all documents where the 4th winding is lower than 0.02

    db.rec.find({"#4 Windingtime" : {"$lt" : 0.02}})
        129

    db.rec2.find({"windingtimes.3" : {"$lt" : 0.02}}).count()
        129 (arrays start with 0)
