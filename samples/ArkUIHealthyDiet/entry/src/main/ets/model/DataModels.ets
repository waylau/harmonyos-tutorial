export enum CategoryId {
  Fruit = 0,
  Vegetable,
  Nut,
  Seafood,
  Dessert
}


export type FoodInfo = {
  id: number
  letter: string
  name: string | Resource
  image: Resource
  categoryId: CategoryId
  calories: number
  protein: number
  fat: number
  carbohydrates: number
  vitaminC: number
}
