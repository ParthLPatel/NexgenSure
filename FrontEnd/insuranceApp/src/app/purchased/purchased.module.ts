import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms'; 
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { MyPlanViewComponent } from './components/my-plan-view/my-plan-view.component';
import { PurchasedProductListComponent } from './components/purchased-product-list/purchased-product-list.component';
import { PurchasedProductCardComponent } from './components/purchased-product-card/purchased-product-card.component';
import {HttpClientModule} from '@angular/common/http';



import {MatToolbarModule} from '@angular/material/toolbar';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input'
import {MatExpansionModule} from '@angular/material/expansion';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import {MatRadioModule} from '@angular/material/radio';
import { MatPaginator } from '@angular/material/paginator';
import { MatPaginatorModule } from '@angular/material/paginator';
import { NgbRatingModule } from '@ng-bootstrap/ng-bootstrap';
import { AddPurchasedComponent } from './components/add-purchased/add-purchased.component';
import { PurchasedBundleListComponent } from './components/purchased-bundle-list/purchased-bundle-list.component';
import { PurchasedBundleCardComponent } from './components/purchased-bundle-card/purchased-bundle-card.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatSelectModule} from '@angular/material/select';
import { BuyProductComponent } from './components/buy-product/buy-product.component';
import {RouterModule} from '@angular/router';

@NgModule({
  declarations: [
    MyPlanViewComponent,
    PurchasedProductListComponent,
    PurchasedProductCardComponent,
    AddPurchasedComponent,
    PurchasedBundleListComponent,
    PurchasedBundleCardComponent,
    BuyProductComponent
  ],
  imports: [
    FormsModule,
    CommonModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,MatExpansionModule,
    MatButtonModule,
    MatTableModule,
    MatSnackBarModule,
    MatPaginatorModule,
    HttpClientModule , 
    NgbRatingModule,
    MatSnackBarModule,
    MatSelectModule,
    RouterModule,
    ReactiveFormsModule
    
  ],
  exports: [
    MyPlanViewComponent,
    PurchasedProductListComponent,
    PurchasedProductCardComponent,
    PurchasedBundleListComponent
  ]
})
export class PurchasedModule { }
