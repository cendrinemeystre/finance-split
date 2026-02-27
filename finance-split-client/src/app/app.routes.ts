import { Routes } from '@angular/router';
import {Home} from './home/home';

export const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'split',
        component: Home
      },
      {
        path: '**',
        redirectTo: 'split'
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'split'
  }
];
