import {Component} from '@angular/core';
import {StatsControllerService} from '../service/stats-controller.service';

@Component({
  selector: 'fs-stats',
  imports: [],
  templateUrl: './stats.html',
  styleUrl: './stats.css',
})
export class Stats {
  constructor(protected readonly statsController: StatsControllerService) {
    statsController.getStats();
  }
}
