import { Component } from '@angular/core';
import { CompanyDataService } from '../../services/company-data.service';
import { CompanyModel } from '../../models/company.model';

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css']
})
export class CompaniesComponent {
  companyObj: CompanyModel = new CompanyModel();
  allCompanyData: any;
  

  constructor(private api:CompanyDataService) { 
    this.getAllCompanies();
  }

  // Get all companies 
  getAllCompanies() {
    this.api.getAllCompanies().subscribe(res => {
      console.log(res);
      this.allCompanyData = res; 
    })
  }

  // Approve company
  approveCompany(data:any){
    this.api.approveCompany(data.companyId).subscribe(res => {
      this.getAllCompanies();
    })
  }

  // Reject company
  rejectCompany(data:any){
    this.api.rejectCompany(data.companyId).subscribe(res => {
      this.getAllCompanies();
    })
  }

  // parsePhone(contactDetails: string): string {
  //   const contactObj = JSON.parse(contactDetails);
  //   return `${contactObj.Phone}`;
  // }

  // parseFax(contactDetails: string): string {
  //   const contactObj = JSON.parse(contactDetails);
  //   return `${contactObj.Fax}`;
  // }

  // parseWebsite(contactDetails: string): string {
  //   const contactObj = JSON.parse(contactDetails);
  //   return `${contactObj.Website}`;
  // }

  // parseInstagram(contactDetails: string): string {
  //   const contactObj = JSON.parse(contactDetails);
  //   if (contactObj && contactObj.Instagram) {
  //     return `${contactObj.Instagram}`;
  //   } else {
  //     return '';
  //   }
  // }
}
