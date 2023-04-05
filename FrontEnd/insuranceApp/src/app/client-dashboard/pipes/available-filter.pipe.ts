import { Pipe, PipeTransform } from '@angular/core';
import { Product } from '../product.model';

@Pipe({
  name: 'availableFilter'
})
export class AvailableFilterPipe implements PipeTransform {

  transform(products: Product[]): Product[] {
    if(products.length==0) return products;
    return products.filter(p=>p.available);
  }

}
