import { User } from "./user.model";

export class Employee{
  id!: number;
  name!: string;
  jobTitle!: string;
  phone!: string;
  imageUrl!: string;
  employeeCode!: string;
  user!: User;
}
