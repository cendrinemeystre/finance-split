import {Component} from '@angular/core';
import {SplitController} from '../service/split-controller.service';
import {NgbTooltip} from '@ng-bootstrap/ng-bootstrap';
import {FsDatePipe} from '../pipe/fs-date-pipe';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'fs-finance',
  imports: [
    NgbTooltip,
    FsDatePipe,
    DatePipe,
  ],
  templateUrl: './finance.html',
  styleUrl: './finance.css',
})
export class Finance {
  constructor(protected serviceController: SplitController) {
    this.serviceController.findAll();
  }

  public remove(id: string): void {
    this.serviceController.remove(id);
  }
}
