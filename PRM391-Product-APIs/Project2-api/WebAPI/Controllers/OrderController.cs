using BusinessObject;
using BusinessObject.DTOs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Security.Principal;

namespace WebAPI.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class OrderController : ControllerBase
    {

        private readonly PlantShopContext _context;

        public OrderController(PlantShopContext context)
        {
            _context = context;
        }

        //Get: api/Order
        [HttpGet]
        public IActionResult GetOrders() {
            List<Order> orders = _context.Orders.Include(x => x.OrderDetails).ToList();
            return orders.Count > 0 ? Ok(orders) : NotFound();
        }

        //Get by CustomerId
        [HttpGet("{id}")]
        public IActionResult GetOrder(int id) { 
        List<Order> orders = _context.Orders.Where(x => x.AccountId== id).ToList();
            return orders.Count > 0 ? Ok(orders) : NotFound();

        }

        [HttpPost]
        public ActionResult<Order> AddOrder(OrderFormat order) {
            Order newOrder = new Order
            {
                AccountId = order.AccountId,
                Address = order.Address,
                isPayed = order.isPayed
                ,ShipFee = order.ShipFee,
                TotalPrice = order.TotalPrice
                ,Account = _context.Accounts.FirstOrDefault(x => x.Id == order.AccountId),
                
            };
            _context.Orders.Add(newOrder);
            _context.SaveChanges();

            foreach (var item in order.Details)
            {
                OrderDetail orderDetail = new OrderDetail
                {
                 
                    Amount = item.Amount,
                    OrderId =newOrder.Id
                    ,ProductId = item.ProductId,
                    
                };
                _context.OrderDetails.Add(orderDetail);
                _context.SaveChanges();
            }
            return CreatedAtAction(nameof(GetOrder), new { id = newOrder.Id}, order);
        }


        [HttpPut]
        public IActionResult PutOrder(int id)
        {
            var newOrder = _context.Orders.FirstOrDefault(x => x.Id == id);
            if ( newOrder is null)
            {
                return BadRequest();
            }

            newOrder.isPayed = true;
            _context.Entry(newOrder).State = EntityState.Modified;

            try
            {
                _context.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!OrderExists(id))
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


        private bool OrderExists(int id)
        {
            return _context.Orders.Any(e => e.Id == id);
        }

    }
}
