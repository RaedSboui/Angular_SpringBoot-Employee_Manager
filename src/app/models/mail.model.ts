import { Employee } from './employee.model';

export class Mail{
  id!: number;
  joinFile!: string;
  constructor(public employee: Employee, public subject: string, public text: string ){}
}
