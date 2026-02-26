import { Routes } from '@angular/router';
import {Home} from './home/home';

export const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'home',
        component: Home
      },
      {
        path: '**',
        redirectTo: 'home'
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'home'
  }
];
