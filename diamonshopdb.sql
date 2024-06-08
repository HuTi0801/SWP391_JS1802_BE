USE [master]
GO
/****** Object:  Database [diamondShopDB]    Script Date: 6/8/2024 3:05:33 PM ******/
CREATE DATABASE [diamondShopDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'diamondShopDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.PHUCDUYEN\MSSQL\DATA\diamondShopDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'diamondShopDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.PHUCDUYEN\MSSQL\DATA\diamondShopDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [diamondShopDB] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [diamondShopDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [diamondShopDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [diamondShopDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [diamondShopDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [diamondShopDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [diamondShopDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [diamondShopDB] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [diamondShopDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [diamondShopDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [diamondShopDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [diamondShopDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [diamondShopDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [diamondShopDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [diamondShopDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [diamondShopDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [diamondShopDB] SET  ENABLE_BROKER 
GO
ALTER DATABASE [diamondShopDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [diamondShopDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [diamondShopDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [diamondShopDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [diamondShopDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [diamondShopDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [diamondShopDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [diamondShopDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [diamondShopDB] SET  MULTI_USER 
GO
ALTER DATABASE [diamondShopDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [diamondShopDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [diamondShopDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [diamondShopDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [diamondShopDB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [diamondShopDB] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [diamondShopDB] SET QUERY_STORE = ON
GO
ALTER DATABASE [diamondShopDB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [diamondShopDB]
GO
/****** Object:  Table [dbo].[account]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[first_name] [varchar](255) NULL,
	[is_active] [bit] NULL,
	[last_name] [varchar](255) NULL,
	[password] [varchar](255) NULL,
	[roles] [varchar](255) NULL,
	[username] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[account_order]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account_order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[account_id] [int] NULL,
	[order_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[email] [varchar](255) NULL,
	[member_level] [varchar](255) NULL,
	[phone_number] [varchar](255) NULL,
	[point] [int] NULL,
	[account_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[date_status_order]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[date_status_order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[date_status] [datetime2](6) NULL,
	[status] [varchar](255) NULL,
	[order_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[diamond]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[carat_weight] [real] NULL,
	[certificate_number] [varchar](255) NULL,
	[clarity] [varchar](255) NULL,
	[color] [varchar](255) NULL,
	[cut] [varchar](255) NULL,
	[image] [varchar](255) NULL,
	[origin] [varchar](255) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[status] [bit] NULL,
	[account_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[diamond_shell]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diamond_shell](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[gender] [varchar](255) NULL,
	[image] [varchar](255) NULL,
	[material] [varchar](255) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[secondary_stone_type] [varchar](255) NULL,
	[status] [bit] NULL,
	[account_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[address] [varchar](255) NULL,
	[customer_name] [varchar](255) NULL,
	[is_cancel] [bit] NOT NULL,
	[number_phone] [varchar](255) NULL,
	[total_price] [float] NOT NULL,
	[warranty_end_date] [datetime2](6) NULL,
	[warranty_start_date] [datetime2](6) NULL,
	[customer_id] [int] NULL,
	[status_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[size] [int] NULL,
	[diamond_id] [int] NULL,
	[diamond_shell_id] [int] NULL,
	[order_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion](
	[id] [int] IDENTITY(1,1) NOT NULL,
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
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion_diamond]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion_diamond](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[diamond_id] [int] NULL,
	[promotion_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion_diamond_shell]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion_diamond_shell](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[diamond_shell_id] [int] NULL,
	[promotion_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[size]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[size](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[size] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[size_diamond_shell]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[size_diamond_shell](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[diamond_shell_id] [int] NULL,
	[size_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[status_order]    Script Date: 6/8/2024 3:05:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[status_order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[status_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Index [UK_jwt2qo9oj3wd7ribjkymryp8s]    Script Date: 6/8/2024 3:05:34 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK_jwt2qo9oj3wd7ribjkymryp8s] ON [dbo].[customer]
(
	[account_id] ASC
)
WHERE ([account_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[account_order]  WITH CHECK ADD  CONSTRAINT [FKnjytchwkjqaoy3vitj6ihpdro] FOREIGN KEY([order_id])
REFERENCES [dbo].[order] ([id])
GO
ALTER TABLE [dbo].[account_order] CHECK CONSTRAINT [FKnjytchwkjqaoy3vitj6ihpdro]
GO
ALTER TABLE [dbo].[account_order]  WITH CHECK ADD  CONSTRAINT [FKr4qmwxhwxsbvgg1128gajovux] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[account_order] CHECK CONSTRAINT [FKr4qmwxhwxsbvgg1128gajovux]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [FKrhuwpv28b7r1xttdimd6uxgq8] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [FKrhuwpv28b7r1xttdimd6uxgq8]
GO
ALTER TABLE [dbo].[date_status_order]  WITH CHECK ADD  CONSTRAINT [FKgdiwgqo47c8je3es052w9v0k7] FOREIGN KEY([order_id])
REFERENCES [dbo].[order] ([id])
GO
ALTER TABLE [dbo].[date_status_order] CHECK CONSTRAINT [FKgdiwgqo47c8je3es052w9v0k7]
GO
ALTER TABLE [dbo].[diamond]  WITH CHECK ADD  CONSTRAINT [FK18ox4xfix4ylqr8fpwbsjystg] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[diamond] CHECK CONSTRAINT [FK18ox4xfix4ylqr8fpwbsjystg]
GO
ALTER TABLE [dbo].[diamond_shell]  WITH CHECK ADD  CONSTRAINT [FK5ur9vvhbu92723iswoxgh9hi3] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[diamond_shell] CHECK CONSTRAINT [FK5ur9vvhbu92723iswoxgh9hi3]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FK7vschkjn6ss3p1ttrt22asq6g] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FK7vschkjn6ss3p1ttrt22asq6g]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FKm66neelxkjwxq0knlq39wbpph] FOREIGN KEY([status_id])
REFERENCES [dbo].[status_order] ([id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FKm66neelxkjwxq0knlq39wbpph]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKlsonu3svt3kmhylu8xt7cymcq] FOREIGN KEY([order_id])
REFERENCES [dbo].[order] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKlsonu3svt3kmhylu8xt7cymcq]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKm9u9g1ywmwgx17wfd29tqc8a5] FOREIGN KEY([diamond_shell_id])
REFERENCES [dbo].[diamond_shell] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKm9u9g1ywmwgx17wfd29tqc8a5]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKrnjq2huvu2qafdqh75ae7g247] FOREIGN KEY([diamond_id])
REFERENCES [dbo].[diamond] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKrnjq2huvu2qafdqh75ae7g247]
GO
ALTER TABLE [dbo].[promotion]  WITH CHECK ADD  CONSTRAINT [FK969ko09pwsnt6k5c6koecm4sa] FOREIGN KEY([account_id])
REFERENCES [dbo].[account] ([id])
GO
ALTER TABLE [dbo].[promotion] CHECK CONSTRAINT [FK969ko09pwsnt6k5c6koecm4sa]
GO
ALTER TABLE [dbo].[promotion_diamond]  WITH CHECK ADD  CONSTRAINT [FK2bnsijtpa7iho8fr27bspjwgi] FOREIGN KEY([diamond_id])
REFERENCES [dbo].[diamond] ([id])
GO
ALTER TABLE [dbo].[promotion_diamond] CHECK CONSTRAINT [FK2bnsijtpa7iho8fr27bspjwgi]
GO
ALTER TABLE [dbo].[promotion_diamond]  WITH CHECK ADD  CONSTRAINT [FK6vq0rio0lu42vfih8pxeaglc4] FOREIGN KEY([promotion_id])
REFERENCES [dbo].[promotion] ([id])
GO
ALTER TABLE [dbo].[promotion_diamond] CHECK CONSTRAINT [FK6vq0rio0lu42vfih8pxeaglc4]
GO
ALTER TABLE [dbo].[promotion_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FKgj8gvyh6098q0co6eekors89g] FOREIGN KEY([promotion_id])
REFERENCES [dbo].[promotion] ([id])
GO
ALTER TABLE [dbo].[promotion_diamond_shell] CHECK CONSTRAINT [FKgj8gvyh6098q0co6eekors89g]
GO
ALTER TABLE [dbo].[promotion_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FKpiyotti1rpfmpwyg3jxp3jhp0] FOREIGN KEY([diamond_shell_id])
REFERENCES [dbo].[diamond_shell] ([id])
GO
ALTER TABLE [dbo].[promotion_diamond_shell] CHECK CONSTRAINT [FKpiyotti1rpfmpwyg3jxp3jhp0]
GO
ALTER TABLE [dbo].[size_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FK37am41nybrg106felrrjsmbae] FOREIGN KEY([size_id])
REFERENCES [dbo].[size] ([id])
GO
ALTER TABLE [dbo].[size_diamond_shell] CHECK CONSTRAINT [FK37am41nybrg106felrrjsmbae]
GO
ALTER TABLE [dbo].[size_diamond_shell]  WITH CHECK ADD  CONSTRAINT [FKnf89brofc668pe6oe2w9uirrp] FOREIGN KEY([diamond_shell_id])
REFERENCES [dbo].[diamond_shell] ([id])
GO
ALTER TABLE [dbo].[size_diamond_shell] CHECK CONSTRAINT [FKnf89brofc668pe6oe2w9uirrp]
GO
ALTER TABLE [dbo].[size]  WITH CHECK ADD CHECK  (([size]>=(1) AND [size]<=(32)))
GO
USE [master]
GO
ALTER DATABASE [diamondShopDB] SET  READ_WRITE 
GO
