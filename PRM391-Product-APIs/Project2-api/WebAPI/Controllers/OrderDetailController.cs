using BusinessObject;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace WebAPI.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class OrderDetailController : ControllerBase
    {
        private readonly PlantShopContext _context;

        public OrderDetailController(PlantShopContext context)
        {
            _context = context;
        }

        // GET: api/OrderDetail
        [HttpGet]
        public IActionResult Get()
        {

            List<OrderDetail> orderList = _context.OrderDetails.Include(x=>x.Product).Include(x=>x.Order).ToList();
            return orderList.Count > 0 ? Ok(orderList) : NotFound();
        }
       
        // GET: api/OrderDetail/5
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var orderDetail = _context.OrderDetails.Include(x => x.Product).Include(x => x.Order).FirstOrDefault(x => x.Id == id);

            if (orderDetail == null)
            {
                return NotFound();
            }

            return Ok(orderDetail);
        }

        // PUT: api/OrderDetail/5
        [HttpPut("{id}")]
        public IActionResult Put(int id, OrderDetail orderDetail)
        {
            if (id != orderDetail.Id)
            {
                return BadRequest();
            }

            _context.Entry(orderDetail).State = EntityState.Modified;

            try
            {
                _context.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!Exists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NotFound();
        }

        // POST: api/OrderDetail
        [HttpPost]
        public ActionResult<OrderDetail> Post(OrderDetail orderDetail)
        {
            _context.Entry(orderDetail).State = EntityState.Added;
            _context.SaveChanges();

            return CreatedAtAction(nameof(Get), new { id = orderDetail.Id }, orderDetail);
        }

        // DELETE: api/OrderDetail/5
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var orderDetail = _context.OrderDetails.FirstOrDefault(x => x.Id == id);
            if (orderDetail == null)
            {
                return NotFound();
            }

            _context.OrderDetails.Remove(orderDetail);
            _context.SaveChanges();

            return Ok(orderDetail);
        }

        private bool Exists(int id)
        {
            return _context.OrderDetails.Any(e => e.Id == id);
        }
    }
}
