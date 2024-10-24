<h1>Recipe Sharing App</h1>
<p>Recipe Sharing App is a platform for food enthusiasts to discover, share, and interact with culinary creations. Users can create and publish recipes, search for dishes based on ingredients or categories, and engage with the community through comments and ratings. The app also supports saving favorite recipes, following other users, and receiving notifications for updates and interactions.</p>

## App Features

1. User Authentication & Authorization
   * JWT-based authentication.
   * OAuth2 integration for social logins.
   * User roles: Admin, Regular User, etc.
   * Password encryption and security measures.

2. User Management
   * User registration and login.
   * Profile management (username, bio, avatar).
   * Password recovery.
   * Saved Recipes: Users can bookmark recipes they want to revisit.

3. Recipe Management
   * CRUD operations for recipes (Create, Read, Update, Delete).
   * Recipes contain title, description, ingredients, instructions, and images.
   * Recipe tags for categorization (e.g., "vegan", "gluten-free").
   * Recipe ratings and reviews by users.
   * Search functionality (by recipe title, description, ingredients, or author name).
4. Ingredient & Recipe Ingredient Management
   * Add ingredients to a shared ingredient database.
   * Associate recipes with specific ingredients in precise quantities.
   * Efficient getOrCreate logic to handle ingredient creation and retrieval.
5. Comments
   * Users can comment on recipes.
   * Nested comments supported.
   * Comments are loaded efficiently to avoid performance issues (e.g., lazy loading, batch fetching).
6. Rating System
   * Users can rate recipes.
   * Ratings are integrated with user accounts and cannot be duplicated.


## Prerequisites 
#### Before you begin, make sure you have the following tools installed on your machine: 
* **Java 11 or higher** 
* **Maven** (for dependency management)  
* **PostgresSQL** (or any database you are using)
* **Git** (for version control)  
* **Spring Boot CLI** (optional, if you want to run the app

## Clone the Repository
#### Start by cloning the repository from GitHub.

```
git clone https://github.com/Olatomiw/Recipe_Sharing_App.git
cd recipe-sharing-app
```

## Installation
1. Set Up PostgreSQL Database 
   * Create a PostgreSQL database (or configure your preferred database).
   * Update the database configuration in src/main/resources/application.properties:
    
    ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/recipe_sharing_db
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password
    
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
    
    ```
    *   Replace your_db_username, your_db_password, and recipe_sharing_db with your PostgreSQL credentials and database name.
2. Configure JWT and OAuth2 Properties
    * Update the security properties for JWT and OAuth2 in application.properties

   ```angular2html
   # JWT configuration
   jwt.secret=your_jwt_secret
   jwt.expiration=86400000 # 1 day
   
   # OAuth2 properties (for Google, Facebook, etc.)
   spring.security.oauth2.client.registration.google.client-id=your_google_client_id
   spring.security.oauth2.client.registration.google.client-secret=your_google_client_secret
   spring.security.oauth2.client.registration.facebook.client-id=your_facebook_client_id
   spring.security.oauth2.client.registration.facebook.client-secret=your_facebook_client_secret
   
   ```
3. Install Dependencies
   * The project uses Maven to manage dependencies. To install the dependencies, run the following command:
   ```
   mvn clean install
   
   ```
4. Usage
   * Registration Endpoint
   `localhost:8080/api/auth/register`
      ```
      "firstName": "Emily",
      "lastName": "Williams",
      "email": "emilywilliams@gmail.com",
      "password": "EmilyW2022"
      ```


## Recipe Creation
`localhost:8080/api/v1/recipe/create`

This API endpoint allows you to create a new recipe.

## Request Body

- `recipeName` (string): The name of the recipe.

- `description` (string): A brief description of the recipe.

- `instructions` (string): Step-by-step instructions to prepare the recipe.

- `image` (string): URL of the image for the recipe.

- `ingredients` (array): An array of objects containing the name of the ingredient and its quantity.


Example:

``` json
{
  "recipeName": "Classic Spaghetti Bolognese",
  "description": "A traditional Italian pasta dish...",
  "instructions": "1. Heat olive oil in a pan. 2. ...",
  "image": "https://example.com/spaghetti-...",
  "ingredients": [
    {
      "ingredientName": "Ground Beef",
      "quantity": 500
    }
  ]
}

 ```

## Response

The response schema for this request is as follows:

``` json
{
  "headers": {},
  "body": {
    "id": "",
    "firstname": "",
    "lastname": "",
    "email": "",
    "password": "",
    "my_comments": [],
    "my_recipes": [
      {
        "id": "",
        "title": "",
        "description": "",
        "instructions": "",
        "createdAt": "",
        "image": "",
        "myComments": [],
        "myRecipeIngredients": [],
        "myRatings": [],
        "mySavedRecipes": [],
        "myTags": []
      }
    ],
    "my_ratings": [],
    "my_saved_recipes": []
  },
  "statusCode": "",
  "statusCodeValue": 0
}

 ```