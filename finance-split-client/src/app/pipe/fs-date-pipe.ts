import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'fsDate',
})
export class FsDatePipe implements PipeTransform {

  transform(value: Date | undefined): string {
    if (!value) {
      return '';
    }
    const valueDate: Date = new Date(value);
    let date: string = '';
    date += valueDate.getDay() + '.' + (valueDate.getMonth() + 1) + '.' + valueDate.getFullYear();
    date += ', ';
    date += valueDate.getHours() + ':' + valueDate.getMinutes() + ':' + valueDate.getSeconds();
    return date;
  }

}
