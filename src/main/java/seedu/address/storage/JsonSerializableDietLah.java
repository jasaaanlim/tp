package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DietLah;
import seedu.address.model.ReadOnlyDietLah;
import seedu.address.model.person.Person;

/**
 * An Immutable DietLah that is serializable to JSON format.
 */
@JsonRootName(value = "dietlah")
class JsonSerializableDietLah {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDietLah} with the given persons.
     */
    @JsonCreator
    public JsonSerializableDietLah(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyDietLah} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableDietLah}.
     */
    public JsonSerializableDietLah(ReadOnlyDietLah source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code DietLah} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DietLah toModelType() throws IllegalValueException {
        DietLah dietLah = new DietLah();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (dietLah.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            dietLah.addPerson(person);
        }
        return dietLah;
    }

}
