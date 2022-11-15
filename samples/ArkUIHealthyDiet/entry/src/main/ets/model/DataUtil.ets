import { FoodInfo } from './DataModels'
import { mockFoods } from '../mock/MockData'

export function getFoods(): Array<FoodInfo> {
  return mockFoods
}
