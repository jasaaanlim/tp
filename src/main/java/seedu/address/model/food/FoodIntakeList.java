package seedu.address.model.food;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.FoodIntakeComparator;
import seedu.address.model.food.exceptions.FoodIntakeNotFoundException;

/**
 * Represents a list of FoodIntakes starting from the specified date.
 */
public class FoodIntakeList {
    private ObservableList<FoodIntake> foodIntakeList;

    /**
     * Constructs a FoodIntakeList.
     */
    public FoodIntakeList() {
        this.foodIntakeList = FXCollections.observableArrayList();
    }

    /**
     * Adds a FoodIntake object to the FoodIntakeList.
     *
     * @param foodIntake FoodIntake object to add to list
     */
    public void addFoodIntake(FoodIntake foodIntake) {
        Food originalFood = foodIntake.getFood();
        String originalName = getOriginalFoodName(originalFood.getName());
        int foodIntakeItemCount = getFoodIntakeItemCount(foodIntake.getDate(), originalName);

        if (foodIntakeItemCount != 0) {
            String foodNameWithCount = originalName + " " + (foodIntakeItemCount + 1);
            foodIntake = new FoodIntake(foodIntake.getDate(), foodNameWithCount, originalFood.getCarbos(), originalFood.getFats(), originalFood.getProteins());
        }
        this.foodIntakeList.add(foodIntake);
    }

    /**
     * Removes a FoodIntake item by the given date and foodintake name
     */
    public void deleteFoodIntake(LocalDate date, String name) throws FoodIntakeNotFoundException {
        FoodIntake foodIntake;
        boolean found = false;
        for (int i = 0; i < this.getFoodIntakeList().size(); i++) {
            foodIntake = this.foodIntakeList.get(i);
            if (foodIntake.getDate().isEqual(date) && foodIntake.getFood().getName().equals(name)) {
                this.foodIntakeList.remove(i);
                reorderDuplicateFoodNames(date, name);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new FoodIntakeNotFoundException();
        }
    }

    /**
     * Updates the FoodIntake object in the FoodIntakeList with another FoodIntake
     *
     * @param index index to replace
     * @param foodIntake FoodIntake object to replace
     */
    public void updateFoodIntake(int index, FoodIntake foodIntake) throws FoodIntakeNotFoundException {
        this.foodIntakeList.set(index, foodIntake);
    }

    /**
     * Gets the first index of a matching food intake item by date and name
     *
     * @return index of FoodIntake
     */
    public int findFoodIntake(LocalDate date, String name) {
        for (int i = 0; i < this.foodIntakeList.size(); i++) {
            if (foodIntakeList.get(i).getDate().isEqual(date)
                    && foodIntakeList.get(i).getFood().getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets the number of FoodIntakes with the matching date and name
     * @param date date to match for
     * @param name name to match for
     *
     * @return count of matching FoodIntakes
     */
    public int getFoodIntakeItemCount(LocalDate date, String name) {
        int count = 0;
        FoodIntake foodIntake;
        for (int i = 0; i < this.foodIntakeList.size(); i++) {
            foodIntake = foodIntakeList.get(i);
            if (foodIntake.getDate().isEqual(date) && getOriginalFoodName(foodIntake.getFood().getName()).equals(name)) {
                count++;
            }
        }
        return count;
    }

    public String getOriginalFoodName(String name) {
        Pattern pattern = Pattern.compile("(.*)( [0-9]*)$");
        Matcher matcher = pattern.matcher(name);
        int matchingCount = 0;

        if (!matcher.matches()) {
            return name;
        } else {
            return matcher.group(1);
        }
    }

    public void reorderDuplicateFoodNames(LocalDate date, String name) {
        String originalFoodName = getOriginalFoodName(name);
        FoodIntake foodIntake;
        int count = 1;
        for (int i = 0; i < this.foodIntakeList.size(); i++) {
            foodIntake = foodIntakeList.get(i);
            if (foodIntake.getDate().isEqual(date) && getOriginalFoodName(foodIntake.getFood().getName()).equals(originalFoodName)) {
                if (count == 1) {
                    foodIntake.getFood().setName(originalFoodName);
                } else {
                    foodIntake.getFood().setName(originalFoodName + " " + count);
                }
                count++;
            }
        }
    }

    /**
     * Gets all FoodIntake object from the FoodIntakeList.
     *
     * @return all FoodIntake object in the list
     */
    public ObservableList<FoodIntake> getFoodIntakeList() {
        Collections.sort(this.foodIntakeList, new FoodIntakeComparator());
        return this.foodIntakeList;
    }


    /**
     * Gets all FoodIntake object based on the date provided.
     *
     * @param date filter date
     * @return all FoodIntake object that are created for that date in String output
     */
    public String getFoodIntakeListByDate(LocalDate date) {
        StringBuilder stringBuilder = new StringBuilder();
        Collections.sort(this.foodIntakeList, new FoodIntakeComparator());
        for (int i = 0; i < this.foodIntakeList.size(); i++) {
            if (foodIntakeList.get(i).getDate().isEqual(date)) {
                stringBuilder.append(foodIntakeList.get(i) + "\n");
            } else if (foodIntakeList.get(i).getDate().isAfter(date)) {
                break;
            }
        }
        if (stringBuilder.toString().isEmpty()) {
            stringBuilder.append("No record found during this date.");
        }
        return stringBuilder.toString();
    }

    /**
     * Gets all FoodIntake object based on the date range provided inclusive in String output.
     *
     * @param from start date
     * @param to   end date
     * @return all FoodIntake object that lies within the date range in String output
     */
    public String getFoodIntakeListByDateRange(LocalDate from, LocalDate to) {
        Collections.sort(this.foodIntakeList, new FoodIntakeComparator());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.foodIntakeList.size(); i++) {
            if (foodIntakeList.get(i).getDate().isEqual(from) || foodIntakeList.get(i).getDate().isEqual(to)) {
                stringBuilder.append(foodIntakeList.get(i) + "\n");
            } else if (foodIntakeList.get(i).getDate().isAfter(from) && foodIntakeList.get(i).getDate().isBefore(to)) {
                stringBuilder.append(foodIntakeList.get(i) + "\n");
            } else if (foodIntakeList.get(i).getDate().isAfter(to)) {
                break;
            }
        }
        if (stringBuilder.toString().isEmpty()) {
            stringBuilder.append("No record found during this period of time.");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodIntakeList other = (FoodIntakeList) o;
        return Objects.equals(foodIntakeList, other.foodIntakeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodIntakeList);
    }
}
