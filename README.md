# Burger Queen

## Project Overview
This JavaFX application simulates an ordering system for RU Burger, a fast-food restaurant. The system allows staff to create and manage orders for various menu items including burgers, sandwiches, beverages, and sides.

## Requirements
- Java 17 or higher
- JavaFX 17.0.6 or higher
- Maven (for dependency management)

## Project Structure
```
Burger Queen/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── cs213project4/
│   │   │               ├── controller/
│   │   │               │   ├── BeverageController.java
│   │   │               │   ├── BurgerController.java
│   │   │               │   ├── ComboController.java
│   │   │               │   ├── Main.java
│   │   │               │   ├── MainController.java
│   │   │               │   ├── OrderController.java
│   │   │               │   ├── SandwichController.java
│   │   │               │   ├── SideController.java
│   │   │               │   └── StoreOrdersController.java
│   │   │               │
│   │   │               └── model/
│   │   │                   ├── AddOns.java
│   │   │                   ├── Beverage.java
│   │   │                   ├── Bread.java
│   │   │                   ├── Burger.java
│   │   │                   ├── Combo.java
│   │   │                   ├── Flavor.java
│   │   │                   ├── MenuItem.java
│   │   │                   ├── Order.java
│   │   │                   ├── Protein.java
│   │   │                   ├── Sandwich.java
│   │   │                   ├── Side.java
│   │   │                   ├── SideOption.java
│   │   │                   ├── Size.java
│   │   │                   └── StoredOrder.java
│   │   │
│   │   └── resources/
│   │       └── com/
│   │           └── example/
│   │               └── cs213project4/
│   │                   ├── BeverageView.fxml
│   │                   ├── BurgerView.fxml
│   │                   ├── ComboView.fxml
│   │                   ├── MainView.fxml
│   │                   ├── OrderView.fxml
│   │                   ├── SandwichView.fxml
│   │                   ├── SideView.fxml
│   │                   ├── StoreOrdersView.fxml
│   │                   │
│   │                   └── image/
│   │                       ├── burger.png
│   │                       ├── sandwich.png
│   │                       ├── beverage.png
│   │                       ├── side.png
│   │                       └── ... (other images)
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── cs213project4/
│                       └── model/
│                           ├── BurgerTest.java
│                           └── SandwichTest.java
│
└── pom.xml
```

## Features
- **Menu Item Management**: Create and customize burgers, sandwiches, beverages, and sides
- **Order Management**: Add items to orders, view current order, remove items, and clear order
- **Store Orders**: View all placed orders, cancel orders, and export orders to a text file
- **Dynamic Pricing**: Prices update in real-time as items are customized
- **Combo Meals**: Option to create combo meals with sandwich/burger, side, and drink

## How to Run
1. Clone the repository
2. Open the project in an IDE (IntelliJ IDEA recommended)
3. Build the project using Maven
4. Run the `Main` class in the `com.example.cs213project4.controller` package

## Menu Options
### Burgers
- **Bread Options**: Brioche, Wheat bread, Pretzel
- **Patty Options**: Single or Double patty
- **Add-ons**: Lettuce, Tomatoes, Onions, Avocado, Cheese

### Sandwiches
- **Bread Options**: Brioche, Wheat bread, Pretzel, Bagel, Sourdough
- **Protein Options**: Roast beef, Salmon, Chicken
- **Add-ons**: Lettuce, Tomatoes, Onions, Avocado, Cheese

### Beverages
- 15 different flavors
- Size options: Small, Medium, Large

### Sides
- Options: Chips, Fries, Onion Rings, Apple Slices
- Size options: Small, Medium, Large

### Combo Meals
- Any sandwich or burger
- Small side (chips or apple)
- Medium drink (cola, tea, or juice)
- Additional $2.00 to the price

## Pricing
- **Sandwich Base Price**:
  - Roast beef: $10.99
  - Chicken: $8.99
  - Salmon: $9.99
  
- **Burger Base Price**:
  - Single patty: $6.99
  - Double patty: Add $2.50 to single patty price
  
- **Add-ons**:
  - Lettuce: $0.30
  - Tomatoes: $0.30
  - Onions: $0.30
  - Avocado: $0.50
  - Cheese: $1.00
  
- **Beverages**:
  - Small: $1.99
  - Medium: $2.49
  - Large: $2.99
  
- **Sides**:
  - Chips: $1.99 (small), +$0.50 (medium), +$1.00 (large)
  - Fries: $2.49 (small), +$0.50 (medium), +$1.00 (large)
  - Onion Rings: $3.29 (small), +$0.50 (medium), +$1.00 (large)
  - Apple Slices: $1.29 (small), +$0.50 (medium), +$1.00 (large)

- **Tax Rate**: 6.625% (New Jersey tax rate)
