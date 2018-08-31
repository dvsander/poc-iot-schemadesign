package com.be.mdb.poc.iot.schemasize;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Arrays;

public class App {

    private static File csvFile = Paths.get("src/main/resources/concatenated_log_All_Channel_iLog.csv").toFile();
    private static final MongoClient M = MongoClients.create(); //localhost:27017
    private static final String DB = "poc-schema-iot";

    public static void main(String[] args) throws Exception {
        assert csvFile.exists() && csvFile.isFile();
        M.getDatabase(DB).drop();

        Iterable<CSVRecord> records = CSVFormat.newFormat(';').withFirstRecordAsHeader().parse(new FileReader(csvFile));
        for (CSVRecord record : records){
            processOption1(record);
            processOption2(record);
        }

    }

    /**
     * Use of array values
     * @param record
     */
    private static void processOption2(CSVRecord record) {
        Document d = new Document()
                .append("Cycle", asLong(record.get(0)))
                .append("Degrees", asLong(record.get(1)))
                .append("Channel", asDecimal(record.get(2)))
                .append("Session", asNumber(record.get(3)))
                .append("Input pressure", asNumber(record.get(4)))
                .append("FMV pressure", asDecimal(record.get(5)))
                .append("DGNS degrees", asDecimal(record.get(6)))
                .append("DGNS pressure", asDecimal(record.get(7)))
                .append("Arrival time", asDecimal(record.get(8)))
                .append("FD detection times", Arrays.asList(
                        asDecimal(record.get(9)),
                        asDecimal(record.get(11))))
                .append("FD detection agrees", Arrays.asList(
                        asDecimal(record.get(10)),
                        asDecimal(record.get(12))))
                .append("LW detection degrees", asDecimal(record.get(13)))
                .append("# valid windings", asDecimal(record.get(14)))
                .append("windingtimes", Arrays.asList(
                        asDecimal(record.get(15)),
                        asDecimal(record.get(17)),
                        asDecimal(record.get(19)),
                        asDecimal(record.get(21)),
                        asDecimal(record.get(23)),
                        asDecimal(record.get(25)),
                        asDecimal(record.get(27)),
                        asDecimal(record.get(29)),
                        asDecimal(record.get(31)),
                        asDecimal(record.get(33)),
                        asDecimal(record.get(35)),
                        asDecimal(record.get(37)),
                        asDecimal(record.get(39)),
                        asDecimal(record.get(41)),
                        asDecimal(record.get(43)),
                        asDecimal(record.get(45))))
                .append("windingtimestamps", Arrays.asList(
                        asDecimal(record.get(16)),
                        asDecimal(record.get(18)),
                        asDecimal(record.get(20)),
                        asDecimal(record.get(22)),
                        asDecimal(record.get(24)),
                        asDecimal(record.get(26)),
                        asDecimal(record.get(28)),
                        asDecimal(record.get(30)),
                        asDecimal(record.get(32)),
                        asDecimal(record.get(34)),
                        asDecimal(record.get(36)),
                        asDecimal(record.get(38)),
                        asDecimal(record.get(40)),
                        asDecimal(record.get(42)),
                        asDecimal(record.get(44)),
                        asDecimal(record.get(46))))
                .append("PFT BrakeDegrees", asDecimal(record.get(47)))
                .append("PFT BrakeSeconds", asDecimal(record.get(48)))
                .append("BurstPosition", asDecimal(record.get(49)))
                .append("ExpTPBurstToPStart", asDecimal(record.get(50)))
                .append("TPBurstToPStart", asDecimal(record.get(51)))
                .append("Degrees Start Insertion", asDecimal(record.get(52)))
                .append("ExpTPStartToPaExpected", asDecimal(record.get(53)))
                .append("TPStartToPaExpected", asDecimal(record.get(54)))
                .append("Px Position", asDecimal(record.get(55)))
                .append("Direction change count", asDecimal(record.get(56)))
                .append("Reverse count", asNumber(record.get(57)));
        M.getDatabase(DB).getCollection("rec2").insertOne(d);
    }


    /**
     * Everything in one document key-value.
     * @param record
     */
    private static void processOption1(CSVRecord record) {
        Document d = new Document()
                .append("Cycle", asLong(record.get(0)))
                .append("Degrees", asLong(record.get(1)))
                .append("Channel", asDecimal(record.get(2)))
                .append("Session", asNumber(record.get(3)))
                .append("Input pressure", asNumber(record.get(4)))
                .append("FMV pressure", asDecimal(record.get(5)))
                .append("DGNS degrees", asDecimal(record.get(6)))
                .append("DGNS pressure", asDecimal(record.get(7)))
                .append("Arrival time", asDecimal(record.get(8)))
                .append("FD1 detection time", asDecimal(record.get(9)))
                .append("FD1 detection degrees", asDecimal(record.get(10)))
                .append("FD2 detection time", asDecimal(record.get(11)))
                .append("FD2 detection degrees", asDecimal(record.get(12)))
                .append("LW detection degrees", asDecimal(record.get(13)))
                .append("# valid windings", asDecimal(record.get(14)))
                .append("#1 Windingtime", asDecimal(record.get(15)))
                .append("#1 Windingtime Timestamp", asDecimal(record.get(16)))
                .append("#2 Windingtime", asDecimal(record.get(17)))
                .append("#2 Windingtime Timestamp", asDecimal(record.get(18)))
                .append("#3 Windingtime", asDecimal(record.get(19)))
                .append("#3 Windingtime Timestamp", asDecimal(record.get(20)))
                .append("#4 Windingtime", asDecimal(record.get(21)))
                .append("#4 Windingtime Timestamp", asDecimal(record.get(22)))
                .append("#5 Windingtime", asDecimal(record.get(23)))
                .append("#5 Windingtime Timestamp", asDecimal(record.get(24)))
                .append("#6 Windingtime", asDecimal(record.get(25)))
                .append("#6 Windingtime Timestamp", asDecimal(record.get(26)))
                .append("#7 Windingtime", asDecimal(record.get(27)))
                .append("#7 Windingtime Timestamp", asDecimal(record.get(28)))
                .append("#8 Windingtime", asDecimal(record.get(29)))
                .append("#8 Windingtime Timestamp", asDecimal(record.get(30)))
                .append("#9 Windingtime", asDecimal(record.get(31)))
                .append("#9 Windingtime Timestamp", asDecimal(record.get(32)))
                .append("#10 Windingtime", asDecimal(record.get(33)))
                .append("#10 Windingtime Timestamp", asDecimal(record.get(34)))
                .append("#11 Windingtime", asDecimal(record.get(35)))
                .append("#11 Windingtime Timestamp", asDecimal(record.get(36)))
                .append("#12 Windingtime", asDecimal(record.get(37)))
                .append("#12 Windingtime Timestamp", asDecimal(record.get(38)))
                .append("#13 Windingtime", asDecimal(record.get(39)))
                .append("#13 Windingtime Timestamp", asDecimal(record.get(40)))
                .append("#14 Windingtime", asDecimal(record.get(41)))
                .append("#14 Windingtime Timestamp", asDecimal(record.get(42)))
                .append("#15 Windingtime", asDecimal(record.get(43)))
                .append("#15 Windingtime Timestamp", asDecimal(record.get(44)))
                .append("#16 Windingtime", asDecimal(record.get(45)))
                .append("#16 Windingtime Timestamp", asDecimal(record.get(46)))
                .append("PFT BrakeDegrees", asDecimal(record.get(47)))
                .append("PFT BrakeSeconds", asDecimal(record.get(48)))
                .append("BurstPosition", asDecimal(record.get(49)))
                .append("ExpTPBurstToPStart", asDecimal(record.get(50)))
                .append("TPBurstToPStart", asDecimal(record.get(51)))
                .append("Degrees Start Insertion", asDecimal(record.get(52)))
                .append("ExpTPStartToPaExpected", asDecimal(record.get(53)))
                .append("TPStartToPaExpected", asDecimal(record.get(54)))
                .append("Px Position", asDecimal(record.get(55)))
                .append("Direction change count", asDecimal(record.get(56)))
                .append("Reverse count", asNumber(record.get(57)));
        M.getDatabase(DB).getCollection("rec").insertOne(d);
    }

    private static int asNumber(String val){
        return Integer.valueOf(val);
    }

    private static long asLong(String val){
        return Long.valueOf(val);
    }

    private static double asDecimal(String val){
        return new BigDecimal(val).doubleValue();
    }

    /**
     * > db.rec2.stats()
     * {
     * 	"ns" : "poc-schema-iot.rec2",
     * 	"size" : 4189405,
     * 	"count" : 4009,
     * 	"avgObjSize" : 1045,
     * 	"storageSize" : 4096,
     * 	"capped" : false,
     *
     * > db.rec.stats()
     * {
     * 	"ns" : "poc-schema-iot.rec",
     * 	"size" : 6426427,
     * 	"count" : 4009,
     * 	"avgObjSize" : 1603,
     * 	"storageSize" : 4096,
     * 	"capped" : false,
     *
     * De data zou ik natuurlijk kunnen wegschrijven als een list van kolomen, maar hoe moet ik dan nadien mijn data bekijken per record??
     *
     * Wat is de definitie van een record? Komt 1 record overeen met 1 meting uit een toestel?
     *
     * Queryen op de collection waar alle csv-kolommen keys zijn in hetzelfde document is standaard. Ik maak hieronder enkele use cases
     *
     *
     */

}

