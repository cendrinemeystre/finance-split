import {Component, inject} from '@angular/core';
import {MatSlideToggle} from '@angular/material/slide-toggle';
import {PersonStore} from '../service/person.store';

@Component({
  selector: 'fs-stats',
  imports: [
    MatSlideToggle
  ],
  templateUrl: './stats.html',
  styleUrl: './stats.css',
})
export class Stats {
  protected readonly personStore = inject(PersonStore);
  darkMode: boolean = true;

  constructor() {
    this.changeTheme();
  }

  public toggle() {
    this.darkMode = !this.darkMode;
    this.changeTheme();
  }

  private changeTheme(): void {
    const html = document.documentElement;
    if (this.darkMode) {
      html.setAttribute('data-bs-theme', 'dark');
    } else {
      html.removeAttribute('data-bs-theme');
    }
  }
}
