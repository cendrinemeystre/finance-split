import {Pipe, PipeTransform} from '@angular/core';
import {PersonDto} from '../core/core-api';

@Pipe({
  name: 'personName'
})
export class PersonNamePipe implements PipeTransform {
  transform(persons: PersonDto[], personId?: string): string {
    return persons.find(p => p.id === personId)?.name ?? 'Unknown';
  }
}
