import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

import { provideNativeDateAdapter, MatNativeDateModule } from '@angular/material/core';
import { importProvidersFrom } from '@angular/core';
import {MatDatepickerModule} from '@angular/material/datepicker';

const extendedConfig = {
  ...appConfig,
  providers: [
    ...(appConfig.providers || []),
    provideNativeDateAdapter(),
    importProvidersFrom(MatNativeDateModule, MatDatepickerModule)
  ]
};

bootstrapApplication(AppComponent, extendedConfig)
  .catch((err) => console.error(err));
