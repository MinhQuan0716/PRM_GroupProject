﻿using BusinessObject;
using BusinessObject.DTOs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;

namespace WebAPI.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly PlantShopContext _context;

        public AccountController(PlantShopContext context)
        {
            _context = context;
        }

        // GET: api/Account
        [HttpGet]
        public IActionResult GetAccounts()
        {
            List<Account> value = _context.Accounts.Include(x => x.OrderDetails).ToList();
            return value.Count > 0 ? Ok(value) : NotFound();
        }

        // GET: api/Account/5
        [HttpGet("{id}")]
        public IActionResult GetAccount(int id)
        {
            var account = _context.Accounts.Include(x => x.OrderDetails).FirstOrDefault(x => x.Id == id);

            return account != null ? Ok(account) : NotFound();
        }
        [HttpGet("login")]
        public IActionResult GetAccount(string username,string password )
        {
            var account = _context.Accounts.Include(x => x.OrderDetails).FirstOrDefault(x => x.Username == username);
            if (account == null) return NotFound();
            return account?.Password == password ? Ok(account) : BadRequest();
        }
        // PUT: api/Account/5
        [HttpPut("{id}")]
        public IActionResult PutAccount(int id, Account account)
        {
            account.Id ??= id;
            _context.Entry(account).State = EntityState.Modified;

            try
            {
                _context.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AccountExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }
      /*  [HttpGet("{username},{password}")]
        public ActionResult<Account> Login(string username,string password)
        { 
            var account= _context.Accounts.SingleOrDefault(x=>x.Username.Equals(username)&&x.Password.Equals(password));
            if(account== null)
            {
                return NotFound();  
            }
            return Ok(account);
        }*/
        // POST: api/Account
        [HttpPost]
        public ActionResult<Account> PostAccount(RegisterFormat account)
        {
            Account newAccount = new Account
            {
                Avatar = "ahihi",
                Fullname = account.FullName,
                Username = account.UserName,
                Password = account.Password,

            };
            _context.Accounts.Add(newAccount);
            _context.SaveChanges();

            return CreatedAtAction(nameof(GetAccount), new { id = newAccount.Id }, newAccount);
        }

        // DELETE: api/Account/5
        [HttpDelete("{id}")]
        public ActionResult<Account> DeleteAccount(int id)
        {
            var account = _context.Accounts.Find(id);
            if (account == null)
            {
                return NotFound();
            }

            _context.Accounts.Remove(account);
            _context.SaveChanges();

            return account;
        }

        private bool AccountExists(int id)
        {
            return _context.Accounts.Any(e => e.Id == id);
        }
    }
}
