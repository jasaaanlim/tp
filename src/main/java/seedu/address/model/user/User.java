package seedu.address.model.user;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.food.Food;
import seedu.address.model.food.FoodIntakeList;
import seedu.address.model.person.Name;



/**
 * Handles the representation of the User class in DieTrack.
 */
public class User {
    // Identity fields
    private final Name name;
    private final Age age;

    // Data fields
    private final Bmi bmi;
    private final List<Food> foodList;
    private final List<FoodIntakeList> foodIntakeList;

    /**
     * Creates a representation of the user with the given parameters.
     * All fields must not be empty.
     * @param name Name of the user
     * @param bmi Bmi object of the user
     * @param foodList Food list of the user
     */
    public User(Name name, Bmi bmi, List<Food> foodList, List<FoodIntakeList> foodIntakeList, Age age) {
        requireAllNonNull(name, bmi, foodList);
        this.name = name;
        this.bmi = bmi;
        this.foodList = foodList;
        this.foodIntakeList = foodIntakeList;
        this.age = age;
    }

    public Name getName() {
        return this.name;
    }

    public Bmi getBmi() {
        return this.bmi;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public Age getAge() {
        return this.age;
    }

    /**
     * Returns true if both users have the same identity and data fields.
     * This defines a stronger notion of equality between two users.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;
        return otherUser.getName().equals(getName())
                && otherUser.getBmi().equals(getBmi())
                && otherUser.getFoodList().equals(getFoodList());
    }
}
