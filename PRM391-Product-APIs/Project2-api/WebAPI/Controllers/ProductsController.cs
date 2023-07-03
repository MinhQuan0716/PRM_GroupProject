using BusinessObject;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json.Linq;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace WebAPI.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class ProductsController : ControllerBase
    {
        private PlantShopContext _dbcontext;

        public ProductsController(PlantShopContext dbcontext)
        {
            _dbcontext = dbcontext;
        }

        // GET: api/<ValuesController>
        [HttpGet]
        public IActionResult Get()
        {
            List<Product> products = _dbcontext.Products.Include(x=>x.OrderDetailsOrdered).ToList();
            return products.Count>0 ? Ok(products):NotFound();
        }

        // GET api/<ValuesController>/5
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            Product? product = _dbcontext.Products.Include(x => x.OrderDetailsOrdered)
                .FirstOrDefault(x => x.Id == id);
            return product != null ? Ok(product) : NotFound();
        }

        // POST api/<ValuesController>
        [HttpPost]
        public IActionResult Post([FromBody] Product value)
        {
            _dbcontext.Products.Add(value);
            return _dbcontext.SaveChanges() > 0 ? Ok() : BadRequest();

        }

        // PUT api/<ValuesController>/5
        [HttpPut("{id}")]
        public IActionResult Put(int id, [FromBody] Product value)
        {
            _dbcontext.Products.Update(value);
            return _dbcontext.SaveChanges() > 0 ? Ok() : NotFound();
        }

        // DELETE api/<ValuesController>/5
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var product = _dbcontext.Products.FirstOrDefault(x => x.Id == id);
            _dbcontext.Products.Remove(product);
            return _dbcontext.SaveChanges() > 0 ? Ok() : NoContent();
        }
    }
}
