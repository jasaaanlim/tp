package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.DietLah;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableDietLahTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableDietLahTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsDietLah.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonDietLah.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonDietLah.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableDietLah dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableDietLah.class).get();
        DietLah dietLahFromFile = dataFromFile.toModelType();
        DietLah typicalPersonsDietLah = TypicalPersons.getTypicalDietLah();
        assertEquals(dietLahFromFile, typicalPersonsDietLah);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableDietLah dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableDietLah.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableDietLah dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableDietLah.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableDietLah.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
