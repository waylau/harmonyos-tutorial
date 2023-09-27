import { CPIData } from '../model/CPIData';
 
@Entry
@Component
struct Index {
  @State cpiData: Array<CPIData> = [
    { id: 1,    month: '2023年08月份',    data: 100.1 },
    { id: 2,    month: '2023年07月份',    data: 99.7 },
    { id: 3,    month: '2023年06月份',    data: 100 },
    { id: 4,    month: '2023年05月份',    data: 100.2 },
    { id: 5,    month: '2023年04月份',    data: 100.1 },
    { id: 6,    month: '2023年03月份',    data: 100.7 },
    { id: 7,    month: '2023年02月份',    data: 101 },
    { id: 8,    month: '2023年01月份',    data: 102.1 }
  ];
 
  build() {
 
  }
}