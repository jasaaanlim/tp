package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalDietLah;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DietLah;
import seedu.address.model.ReadOnlyDietLah;

public class JsonDietLahStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonDietLahStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readDietLah_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readDietLah(null));
    }

    private java.util.Optional<ReadOnlyDietLah> readDietLah(String filePath) throws Exception {
        return new JsonDietLahStorage(Paths.get(filePath)).readDietLah(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDietLah("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readDietLah("notJsonFormatDietLah.json"));
    }

    @Test
    public void readDietLah_invalidPersonDietLah_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readDietLah("invalidPersonDietLah.json"));
    }

    @Test
    public void readDietLah_invalidAndValidPersonDietLah_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readDietLah("invalidAndValidPersonDietLah.json"));
    }

    @Test
    public void readAndSaveDietLah_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempDietLah.json");
        DietLah original = getTypicalDietLah();
        JsonDietLahStorage jsonDietLahStorage = new JsonDietLahStorage(filePath);

        // Save in new file and read back
        jsonDietLahStorage.saveDietLah(original, filePath);
        ReadOnlyDietLah readBack = jsonDietLahStorage.readDietLah(filePath).get();
        assertEquals(original, new DietLah(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonDietLahStorage.saveDietLah(original, filePath);
        readBack = jsonDietLahStorage.readDietLah(filePath).get();
        assertEquals(original, new DietLah(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonDietLahStorage.saveDietLah(original); // file path not specified
        readBack = jsonDietLahStorage.readDietLah().get(); // file path not specified
        assertEquals(original, new DietLah(readBack));

    }

    @Test
    public void saveDietLah_nullDietLah_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDietLah(null, "SomeFile.json"));
    }

    /**
     * Saves {@code dietLah} at the specified {@code filePath}.
     */
    private void saveDietLah(ReadOnlyDietLah dietLah, String filePath) {
        try {
            new JsonDietLahStorage(Paths.get(filePath))
                    .saveDietLah(dietLah, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDietLah_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDietLah(new DietLah(), null));
    }
}
