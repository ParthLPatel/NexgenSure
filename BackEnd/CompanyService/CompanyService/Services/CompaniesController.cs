﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using CompanyService.Repository;
using System.Net;

namespace CompanyService.Services
{
    [Route("api/company")]
    [ApiController]
    public class CompaniesController : ControllerBase
    {
        private readonly CompanyContext _context;

        public CompaniesController(CompanyContext context)
        {
            _context = context;
        }

        // GET: api/company
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Company>>> GetCompanies()
        {
            if (_context.Companies == null)
            {
                return NotFound("No companies to display.");
            }
            return await _context.Companies.ToListAsync();
        }

        // GET: api/company/id/id
        [HttpGet("id/{id}")]
        public async Task<ActionResult<Company>> GetCompany(int id)
        {
            if (_context.Companies == null)
            {
                return NotFound("No companies to display.");
            }
            var company = await _context.Companies.FindAsync(id);

            if (company == null)
            {
                return NotFound($"Company with ID {id} does not exist.");
            }

            return company;
        }

        // GET: api/company/view/{number to display}
        [HttpGet("view/{num_companies}")]
        public async Task<ActionResult<IEnumerable<Company>>> GetCompanies(int num_companies)
        {
            if (_context.Companies == null)
            {
                return NotFound("No companies to display.");
            }
            if (num_companies <= 0)
            {
                return BadRequest("Please enter a valid integer above 0."); 
            }
            var companies = await _context.Companies.Take(num_companies).ToListAsync();
            if (!companies.Any())
            {
                return NotFound("No companies to display.");
            }
            return companies;
        }


        // GET: api/company/view/{name}
        [HttpGet("name/{name}")]
        public async Task<ActionResult<IEnumerable<Company>>> GetCompany(string name)
        {
            if (_context.Companies == null)
            {
                return NotFound("No companies to display.");
            }
            return await _context.Companies.Where<Company>(c => c.Name.ToLower().Contains(name.ToLower())).ToListAsync();
        }

        // PUT: api/company/update/id
        [HttpPut("update/{id}")]
        public async Task<IActionResult> PutCompany(int id, Company company)
        {
            if (id != company.CompanyId)
            {
                return BadRequest($"Entered ID {id} and company.CompanyId {company.CompanyId} do not match. Could not update company information in the database.");
            }

            _context.Entry(company).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CompanyExists(id))
                {
                    return NotFound($"Company with ID {id} does not exist. Could not update company information in the database.");
                }
                else
                {
                    return BadRequest("Could not update company information in the database");
                }
            }

            return Ok();
        }

        // POST: api/company/add
        [HttpPost("add")]
        public async Task<ActionResult<Company>> PostCompany(Company company)
        {
            if (_context.Companies == null)
            {
                return NotFound("No companies to display.");
            }
            _context.Companies.Add(company);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCompany", new { id = company.CompanyId }, company);
        }

        // DELETE: api/company/delete/id
        [HttpDelete("delete/{id}")]
        public async Task<IActionResult> DeleteCompany(int id)
        {
            if (_context.Companies == null)
            {
                return NotFound("No companies to display.");
            }
            var company = await _context.Companies.FindAsync(id);
            if (company == null)
            {
                return NotFound($"Company with ID {id} could not be found. Could not delete company.");
            }

            _context.Companies.Remove(company);
            await _context.SaveChangesAsync();

            return Ok();
        }

        // POST (approve company): api/company/approve/id
        [HttpPut("approve/{id}")]
        public async Task<IActionResult> ApproveCompany(int id)
        {
            if (_context.Companies == null)
            {
                return NotFound("No companies to display.");
            }
            var company = await _context.Companies.FindAsync(id);
            if (company == null)
            {
                return NotFound($"Company with ID {id} could not be found. Could not approve company.");
            }

            company.IsApproved = true;

            _context.Entry(company).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CompanyExists(id))
                {
                    return NotFound($"Company with ID {id} does not exist. Could not approve company.");
                }
                else
                {
                    return BadRequest("Could not approve company.");
                }
            }

            return Ok();
        }

        // POST (disapprove company): api/company/disapprove/id
        [HttpPut("disapprove/{id}")]
        public async Task<IActionResult> DisapproveCompany(int id)
        {
            if (_context.Companies == null)
            {
                return NotFound("No companies to display.");
            }
            var company = await _context.Companies.FindAsync(id);
            if (company == null)
            {
                return NotFound($"Company with ID {id} could not be found. Could not disapprove company.");
            }

            company.IsApproved = false;

            _context.Entry(company).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CompanyExists(id))
                {
                    return NotFound($"Company with ID {id} does not exist. Could not disapprove company.");
                }
                else
                {
                    return BadRequest("Could not disapprove company.");
                }
            }

            return Ok();
        }

        private bool CompanyExists(int id)
        {
            return (_context.Companies?.Any(e => e.CompanyId == id)).GetValueOrDefault();
        }
    }
}
