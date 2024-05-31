USE [master]
GO
/****** Object:  Database [DiamondShopDB]    Script Date: 5/31/2024 2:21:04 AM ******/
CREATE DATABASE [DiamondShopDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DiamondShopDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.PHUCDUYEN\MSSQL\DATA\DiamondShopDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'DiamondShopDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.PHUCDUYEN\MSSQL\DATA\DiamondShopDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [DiamondShopDB] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DiamondShopDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DiamondShopDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DiamondShopDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DiamondShopDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DiamondShopDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DiamondShopDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [DiamondShopDB] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [DiamondShopDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DiamondShopDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DiamondShopDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DiamondShopDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DiamondShopDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DiamondShopDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DiamondShopDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DiamondShopDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DiamondShopDB] SET  ENABLE_BROKER 
GO
ALTER DATABASE [DiamondShopDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DiamondShopDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DiamondShopDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DiamondShopDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DiamondShopDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DiamondShopDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DiamondShopDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DiamondShopDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [DiamondShopDB] SET  MULTI_USER 
GO
ALTER DATABASE [DiamondShopDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DiamondShopDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DiamondShopDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DiamondShopDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DiamondShopDB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DiamondShopDB] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [DiamondShopDB] SET QUERY_STORE = ON
GO
ALTER DATABASE [DiamondShopDB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [DiamondShopDB]
GO
/****** Object:  Table [dbo].[account]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account](
	[account_id] [int] IDENTITY(1,1) NOT NULL,
	[first_name] [varchar](255) NULL,
	[is_active] [bit] NULL,
	[last_name] [varchar](255) NULL,
	[password] [varchar](255) NULL,
	[username] [varchar](255) NULL,
	[role_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[account_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[customer_id] [int] IDENTITY(1,1) NOT NULL,
	[email] [varchar](255) NULL,
	[member_level] [varchar](255) NULL,
	[phone_number] [varchar](255) NULL,
	[point] [int] NULL,
	[account_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[customer_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[diamond]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond](
	[diamond_id] [int] IDENTITY(1,1) NOT NULL,
	[carat_weight] [real] NOT NULL,
	[certificate_number] [varchar](255) NULL,
	[clarity] [varchar](255) NULL,
	[color] [varchar](255) NULL,
	[cut] [varchar](255) NULL,
	[image] [varchar](255) NULL,
	[origin] [varchar](255) NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
	[account_id] [int] NULL,
	[status] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[diamond_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[diamond_request]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond_request](
	[diamond_id] [int] IDENTITY(1,1) NOT NULL,
	[carat_weight] [real] NOT NULL,
	[certificate_number] [varchar](255) NULL,
	[clarity] [varchar](255) NULL,
	[color] [char](1) NOT NULL,
	[cut] [varchar](255) NULL,
	[image] [varchar](255) NULL,
	[origin] [varchar](255) NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[diamond_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[diamond_shell]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond_shell](
	[diamond_shell_id] [int] IDENTITY(1,1) NOT NULL,
	[gender] [varchar](255) NULL,
	[image] [varchar](255) NULL,
	[material] [varchar](255) NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
	[secondary_stone_type] [varchar](255) NULL,
	[account_id] [int] NULL,
	[status] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[diamond_shell_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[diamond_shell_request]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond_shell_request](
	[diamond_shell_id] [int] IDENTITY(1,1) NOT NULL,
	[gender] [varchar](255) NULL,
	[image] [varchar](255) NULL,
	[material] [varchar](255) NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
	[secondary_stone_type] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[diamond_shell_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order](
	[order_id] [int] IDENTITY(1,1) NOT NULL,
	[address] [varchar](255) NULL,
	[purchase_date] [datetime2](6) NULL,
	[total_price] [float] NULL,
	[warranty_end_date] [datetime2](6) NULL,
	[warranty_start_date] [datetime2](6) NULL,
	[account_id] [int] NULL,
	[customer_id] [int] NULL,
	[status_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[order_detail_id] [int] IDENTITY(1,1) NOT NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[diamond_id] [int] NULL,
	[diamond_shell_id] [int] NULL,
	[order_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[order_detail_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion](
	[promotion_id] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](255) NULL,
	[discount_percent] [real] NULL,
	[end_date] [datetime2](6) NULL,
	[member_level_promotion] [varchar](255) NULL,
	[promotion_name] [varchar](255) NULL,
	[start_date] [datetime2](6) NULL,
	[type] [varchar](255) NULL,
	[account_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[promotion_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion_diamond]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion_diamond](
	[promotion_diamonds_id] [int] IDENTITY(1,1) NOT NULL,
	[diamond_id] [int] NULL,
	[promotion_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[promotion_diamonds_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion_diamond_shell]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion_diamond_shell](
	[promotion_diamond_shell_id] [int] IDENTITY(1,1) NOT NULL,
	[diamond_shell_id] [int] NULL,
	[promotion_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[promotion_diamond_shell_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles](
	[role_id] [int] IDENTITY(1,1) NOT NULL,
	[role_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[size]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[size](
	[size_id] [int] IDENTITY(1,1) NOT NULL,
	[size] [real] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[size_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[size_diamond_shell]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[size_diamond_shell](
	[size_diamond_shell_id] [int] IDENTITY(1,1) NOT NULL,
	[diamond_shell_id] [int] NULL,
	[size_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[size_diamond_shell_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[size_request]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[size_request](
	[size_id] [int] IDENTITY(1,1) NOT NULL,
	[size] [real] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[size_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[status]    Script Date: 5/31/2024 2:21:04 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[status](
	[status_id] [int] IDENTITY(1,1) NOT NULL,
	[status_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[status_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Index [UK_jwt2qo9oj3wd7ribjkymryp8s]    Script Date: 5/31/2024 2:21:04 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK_jwt2qo9oj3wd7ribjkymryp8s] ON [dbo].[customer]
(
	[account_id] ASC
)
WHERE ([account_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[account]  WITH CHECK ADD  CONSTRAINT [FK8er7oee16p5tfbl2nhn73jw56] FOREIGN KEY([role_id])
REFERENCES [dbo].[roles] ([role_id])
GO
ALTER TABLE [dbo].[account] CHECK CONSTRAINT [FK8er7oee16p5tfbl2nhn73jw56]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [FKrhuwpv28b7r1xttdimd6uxgq8] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([account_id])
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [FKrhuwpv28b7r1xttdimd6uxgq8]
GO
ALTER TABLE [dbo].[diamond]  WITH CHECK ADD  CONSTRAINT [FK18ox4xfix4ylqr8fpwbsjystg] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([account_id])
GO
ALTER TABLE [dbo].[diamond] CHECK CONSTRAINT [FK18ox4xfix4ylqr8fpwbsjystg]
GO
ALTER TABLE [dbo].[diamond_shell]  WITH CHECK ADD  CONSTRAINT [FK5ur9vvhbu92723iswoxgh9hi3] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([account_id])
GO
ALTER TABLE [dbo].[diamond_shell] CHECK CONSTRAINT [FK5ur9vvhbu92723iswoxgh9hi3]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FK7vschkjn6ss3p1ttrt22asq6g] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([customer_id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FK7vschkjn6ss3p1ttrt22asq6g]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FKg3v35vmwvm8s77rq3r4n6t8x0] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([account_id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FKg3v35vmwvm8s77rq3r4n6t8x0]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FKitqbxhdv04u3h6j7wkct3x7l7] FOREIGN KEY([status_id])
REFERENCES [dbo].[status] ([status_id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FKitqbxhdv04u3h6j7wkct3x7l7]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKlsonu3svt3kmhylu8xt7cymcq] FOREIGN KEY([order_id])
REFERENCES [dbo].[order] ([order_id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKlsonu3svt3kmhylu8xt7cymcq]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKm9u9g1ywmwgx17wfd29tqc8a5] FOREIGN KEY([diamond_shell_id])
REFERENCES [dbo].[diamond_shell] ([diamond_shell_id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKm9u9g1ywmwgx17wfd29tqc8a5]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKrnjq2huvu2qafdqh75ae7g247] FOREIGN KEY([diamond_id])
REFERENCES [dbo].[diamond] ([diamond_id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKrnjq2huvu2qafdqh75ae7g247]
GO
ALTER TABLE [dbo].[promotion]  WITH CHECK ADD  CONSTRAINT [FK969ko09pwsnt6k5c6koecm4sa] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([account_id])
GO
ALTER TABLE [dbo].[promotion] CHECK CONSTRAINT [FK969ko09pwsnt6k5c6koecm4sa]
GO
ALTER TABLE [dbo].[promotion_diamond]  WITH CHECK ADD  CONSTRAINT [FK2bnsijtpa7iho8fr27bspjwgi] FOREIGN KEY([diamond_id])
REFERENCES [dbo].[diamond] ([diamond_id])
GO
ALTER TABLE [dbo].[promotion_diamond] CHECK CONSTRAINT [FK2bnsijtpa7iho8fr27bspjwgi]
GO
ALTER TABLE [dbo].[promotion_diamond]  WITH CHECK ADD  CONSTRAINT [FK6vq0rio0lu42vfih8pxeaglc4] FOREIGN KEY([promotion_id])
REFERENCES [dbo].[promotion] ([promotion_id])
GO
ALTER TABLE [dbo].[promotion_diamond] CHECK CONSTRAINT [FK6vq0rio0lu42vfih8pxeaglc4]
GO
ALTER TABLE [dbo].[promotion_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FKgj8gvyh6098q0co6eekors89g] FOREIGN KEY([promotion_id])
REFERENCES [dbo].[promotion] ([promotion_id])
GO
ALTER TABLE [dbo].[promotion_diamond_shell] CHECK CONSTRAINT [FKgj8gvyh6098q0co6eekors89g]
GO
ALTER TABLE [dbo].[promotion_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FKpiyotti1rpfmpwyg3jxp3jhp0] FOREIGN KEY([diamond_shell_id])
REFERENCES [dbo].[diamond_shell] ([diamond_shell_id])
GO
ALTER TABLE [dbo].[promotion_diamond_shell] CHECK CONSTRAINT [FKpiyotti1rpfmpwyg3jxp3jhp0]
GO
ALTER TABLE [dbo].[size_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FK37am41nybrg106felrrjsmbae] FOREIGN KEY([size_id])
REFERENCES [dbo].[size] ([size_id])
GO
ALTER TABLE [dbo].[size_diamond_shell] CHECK CONSTRAINT [FK37am41nybrg106felrrjsmbae]
GO
ALTER TABLE [dbo].[size_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FKnf89brofc668pe6oe2w9uirrp] FOREIGN KEY([diamond_shell_id])
REFERENCES [dbo].[diamond_shell] ([diamond_shell_id])
GO
ALTER TABLE [dbo].[size_diamond_shell] CHECK CONSTRAINT [FKnf89brofc668pe6oe2w9uirrp]
GO
ALTER TABLE [dbo].[diamond]  WITH CHECK ADD CHECK  (([carat_weight]>=(3) AND [carat_weight]<=(9223372036854775807.)))
GO
ALTER TABLE [dbo].[diamond]  WITH CHECK ADD CHECK  (([quantity]>=(0)))
GO
ALTER TABLE [dbo].[diamond_request]  WITH CHECK ADD CHECK  (([carat_weight]>=(3) AND [carat_weight]<=(9223372036854775807.)))
GO
ALTER TABLE [dbo].[diamond_request]  WITH CHECK ADD CHECK  (([quantity]>=(0)))
GO
ALTER TABLE [dbo].[diamond_shell]  WITH CHECK ADD CHECK  (([quantity]>=(0)))
GO
ALTER TABLE [dbo].[diamond_shell_request]  WITH CHECK ADD CHECK  (([quantity]>=(0)))
GO
ALTER TABLE [dbo].[size]  WITH CHECK ADD CHECK  (([size]>=(1) AND [size]<=(32)))
GO
ALTER TABLE [dbo].[size_request]  WITH CHECK ADD CHECK  (([size]>=(1) AND [size]<=(32)))
GO
USE [master]
GO
ALTER DATABASE [DiamondShopDB] SET  READ_WRITE 
GO
