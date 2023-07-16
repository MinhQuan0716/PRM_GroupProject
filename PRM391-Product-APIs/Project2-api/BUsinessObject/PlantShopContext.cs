using System;
using System.Collections.Generic;
using Bogus;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace BusinessObject
{
    public partial class PlantShopContext : DbContext
    {
        public PlantShopContext()
        {
        }

        public PlantShopContext(DbContextOptions<PlantShopContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Account> Accounts { get; set; }
        public virtual DbSet<OrderDetail> OrderDetails { get; set; }
        public virtual DbSet<Product> Products { get; set; }
        public virtual DbSet<Order> Orders { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseSqlServer("server= (local); database = GroupProject;uid=sa; pwd =12345; TrustServerCertificate=True;");
            }
            base.OnConfiguring(optionsBuilder);
        }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<OrderDetail>().HasOne(x => x.Product).WithMany(x => x.OrderDetailsOrdered).HasForeignKey(x => x.ProductId);
            builder.Entity<OrderDetail>().HasOne(x => x.Order).WithMany(x => x.OrderDetails).HasForeignKey(x => x.OrderId);
            OnModelCreatingPartial(builder);
        }
        public void SeedData()
        {
            var a = Accounts;
            var p = Products;
            var od = OrderDetails;
            if (a.Any() || p.Any() || od.Any())
            {
                // Data already exists, no need to seed
                return;
            }
            Console.WriteLine("Seeding Data");
            var accounts = CreateAccountSeedData(10);
            var products = CreateProductSeedData(10);
            var orderDetails = CreateOrderDetailSeedData(accounts, products);

            Accounts.AddRange(accounts);
            Products.AddRange(products);
            OrderDetails.AddRange(orderDetails);
            SaveChanges();
        }
        private List<Account> CreateAccountSeedData(int count)
        {
            var faker = new Faker<Account>()
                .RuleFor(a => a.Username, f => f.Internet.UserName())
                .RuleFor(a => a.Password, f => f.Internet.Password())
                .RuleFor(a => a.Fullname, f => f.Name.FullName())
                .RuleFor(a => a.Avatar, f => f.Image.PicsumUrl());
            return faker.Generate(count);
        }

        private List<Product> CreateProductSeedData(int count)
        {
            var faker = new Faker<Product>()
                .RuleFor(p => p.ProductName, f => f.Commerce.ProductName())
                .RuleFor(p => p.Price, f => f.Random.Number(1, 10) * 100)
                .RuleFor(p => p.Description, f => f.Lorem.Sentence())
                .RuleFor(p => p.ImgPath, f => f.Image.PicsumUrl());

            return faker.Generate(count);
        }
        private List<OrderDetail> CreateOrderDetailSeedData(List<Account> accounts, List<Product> products)
        {
            var orderDetails = new List<OrderDetail>();
            var random = new Random();

            var usedCombinations = new HashSet<(int, int)>();

            int numberOfEntities = Math.Min(10, accounts.Count);

            while (orderDetails.Count < numberOfEntities && usedCombinations.Count < accounts.Count * products.Count)
            {
                Console.WriteLine($"Adding New OrderDetail");
                var accIndex = random.Next(accounts.Count);
                var prodIndex = random.Next(products.Count);
                var account = accounts[accIndex];
                var product = products[prodIndex];
                var combination = (accIndex, prodIndex);
                Console.WriteLine($"{combination} {!usedCombinations.Contains(combination)}");

                if (!usedCombinations.Contains(combination))
                {
                    orderDetails.Add(new OrderDetail
                    {
                        Product = product
                    });

                    usedCombinations.Add(combination);
                    Console.WriteLine($"Generated OrderDetail with AccountId: {account.Id}, ProductId: {product.Id}");
                }
            }

            Console.WriteLine($"Total OrderDetails generated: {orderDetails.Count}");

            return orderDetails;
        }
        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
