import {Component, signal} from '@angular/core';
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {NgClass} from '@angular/common';
import {FieldTree, form, FormField, min, required} from '@angular/forms/signals';
import {SplitController} from '../service/split-controller.service';
import {SplitEntry} from '../service/split-entry';

@Component({
  selector: 'fs-split',
  imports: [
    MatSlideToggle,
    NgClass,
    FormField
  ],
  templateUrl: './split.html',
  styleUrl: './split.css',
})
export class Split {
  splitEntryForm: FieldTree<SplitEntry>;
  suggestions: string[] = [
    'Ibby',
    'ichoufe',
    'Galaxus/Digitec',
    'Bstelle',
    'Mobility'
  ]

  constructor(protected serviceController: SplitController) {
    const splitEntry = signal<SplitEntry>({
      amount: 0,
      person: false,
      description: ''
    })
    this.splitEntryForm = form(splitEntry, entry => {
      required(entry.amount);
      required(entry.description);
      min(entry.amount, 0, {message: 'It must be a positive number'});
    });
  }

  public add(): void {
    const entry: SplitEntry = {
      person: this.splitEntryForm.person().value(),
      amount: this.splitEntryForm.amount().value(),
      description: this.splitEntryForm.description().value(),
    };
    this.serviceController.add(entry);
  }
}
