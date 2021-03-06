package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.food.Food;
import seedu.address.model.food.FoodIntake;
import seedu.address.model.food.FoodIntakeList;
import seedu.address.model.food.UniqueFoodList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.user.User;
import seedu.address.model.util.TemplateInitializer;

/**
 * Wraps all data at the DietLah level
 */
public class DietLah implements ReadOnlyDietLah {
    private final UniquePersonList persons;

    private User user;

    private UniqueFoodList foodList;

    private FoodIntakeList foodIntakeList;
    //Used to have an old comment here, removed due to checkstyle error. Refer to old template for more.
    {
        persons = new UniquePersonList();
        foodList = new UniqueFoodList();
        foodIntakeList = new FoodIntakeList();


    }

    public DietLah() {
    }

    /**
     * Creates an DietLah using the Persons in the {@code toBeCopied}.
     */
    public DietLah(ReadOnlyDietLah toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Creates an DietLah using the Persons in the {@code toBeCopied}. Adds
     * the associated {@code FoodList} {@code FoodIntakeList}.
     */
    public DietLah(ReadOnlyDietLah toBeCopied,
                       UniqueFoodList uniqueFoodList,
                       FoodIntakeList foodIntakeList,
                       User user) {
        this();
        resetData(toBeCopied);
        this.foodList = uniqueFoodList;
        this.foodIntakeList = foodIntakeList;
        this.user = user;
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code DietLah} with {@code newData}.
     */
    public void resetData(ReadOnlyDietLah newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code DietLah}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DietLah // instanceof handles nulls
                && persons.equals(((DietLah) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    /**
     * Returns true if a person with the same identity as {@code food} exists in the food list.
     */
    public boolean hasFoodItem(Food food) {
        requireNonNull(food);
        return foodList.hasFoodItem(food);
    }

    /**
     * Adds a food item into the food list.
     * The food item must not exist in the food list.
     *
     * @param food food item
     */
    public void addFoodItem(Food food) {
        foodList.addFoodItem(food);
    }

    /**
     * Deletes a food item in the food list by its name.
     *
     * @param index food index
     */
    public void deleteFoodItem(int index) {
        foodList.deleteFoodItem(index);
    }

    /**
     * Updates a food item in the food list
     * The food item must exist in the food list.
     *
     * @param food updated food item
     */
    public void updateFoodItem(Food food) {
        foodList.updateFoodItem(food);
    }

    /**
     * Gets the unique food list.
     *
     * @return a unique food list
     */
    public UniqueFoodList getFoodList() {
        return foodList;
    }

    /**
     * Lists all food items from the food list.
     *
     * @return string output of the food list
     */
    public String listFoodItem() {
        return foodList.listAllFoodItem();
    }

    /**
     * Adds the user information.
     *
     * @param user User object
     */
    public void addUser(User user) {
        this.user = user;
    }

    /**
     * Returns whether user information has been initialized.
     *
     * @return Boolean indicating whether user is initialized
     */
    public boolean hasUser() {
        return this.user != null;
    }

    /**
     * Returns the user information.
     *
     * @return User object
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Adds a FoodIntake to the FoodIntakeList given the date and Food
     * The FoodIntake's Food name may be appended with a duplicate count.
     *
     * @param date date of Food intake object
     * @param food food item of Food intake object
     *
     * @return Food that was successfully added to the FoodIntakeList.
     */
    public Food addFoodIntake(LocalDate date, Food food) {
        return foodIntakeList.addFoodIntake(new FoodIntake(date, food));
    }

    /**
     * Updates the FoodIntake object in the FoodIntakeList
     *
     * @param index index to replace
     * @param foodIntake FoodIntake object to replace
     */
    public void updateFoodIntake(int index, FoodIntake foodIntake) {
        foodIntakeList.updateFoodIntake(index, foodIntake);
    }

    /**
     * Returns the FoodIntakeList instance.
     * @return FoodIntakeList instance
     */
    public FoodIntakeList getFoodIntakeList() {
        return foodIntakeList;
    }

    /**
     * Resets the current application data by setting to default values from TemplateInitializer.
     */
    public void resetToTemplate(UniqueFoodList foodlist, FoodIntakeList foodIntakeList) {
        TemplateInitializer templateInitializer = new TemplateInitializer();
        this.foodList = foodlist;
        this.foodIntakeList = foodIntakeList;
        this.user = templateInitializer.createUser(this.foodList, this.foodIntakeList);
    }

    /**
     * Resets the current application data to blank state.
     */
    public void resetToBlank(UniqueFoodList foodlist, FoodIntakeList foodIntakeList) {
        this.user = null;
        this.foodList = foodlist;
        this.foodIntakeList = foodIntakeList;
    }
}
