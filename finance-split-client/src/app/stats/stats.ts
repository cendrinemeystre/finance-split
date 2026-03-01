import {Component} from '@angular/core';
import {StatsControllerService} from '../service/stats-controller.service';
import {MatSlideToggle} from '@angular/material/slide-toggle';

@Component({
  selector: 'fs-stats',
  imports: [
    MatSlideToggle
  ],
  templateUrl: './stats.html',
  styleUrl: './stats.css',
})
export class Stats {
  darkMode: boolean = true;

  constructor(protected readonly statsController: StatsControllerService) {
    statsController.getStats();
    this.changeTheme();
  }

  public toggle(){
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
