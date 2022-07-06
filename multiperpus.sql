-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 06, 2022 at 07:10 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `multiperpus`
--

-- --------------------------------------------------------

--
-- Table structure for table `daftar_kunjungan`
--

CREATE TABLE `daftar_kunjungan` (
  `tgl_input` datetime NOT NULL,
  `thn_ajaran` varchar(20) DEFAULT NULL,
  `tgl_msk` varchar(20) NOT NULL,
  `jam_msk` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kelas` varchar(100) NOT NULL,
  `jenis_kunjungan` varchar(20) NOT NULL,
  `keterangan` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pinjaman_barang`
--

CREATE TABLE `pinjaman_barang` (
  `tgl_input` datetime NOT NULL,
  `thn_ajaran` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kelas` varchar(10) NOT NULL,
  `kode_barang` varchar(20) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `tgl_pinjam` varchar(20) NOT NULL,
  `tgl_kembali` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pinjaman_buku`
--

CREATE TABLE `pinjaman_buku` (
  `tgl_input` datetime NOT NULL,
  `thn_ajaran` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kelas` varchar(10) NOT NULL,
  `kode_buku` varchar(20) NOT NULL,
  `judul` varchar(100) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `tgl_pinjam` varchar(20) NOT NULL,
  `tgl_kembali` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `stok_barang`
--

CREATE TABLE `stok_barang` (
  `kode_barang` varchar(20) NOT NULL,
  `nama_barang` varchar(100) DEFAULT NULL,
  `merek` varchar(100) DEFAULT NULL,
  `kadaluarsa` varchar(20) DEFAULT NULL,
  `kategori` varchar(20) DEFAULT NULL,
  `jumlah` int(5) DEFAULT NULL,
  `gambar` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `stok_buku`
--

CREATE TABLE `stok_buku` (
  `kode_buku` varchar(20) NOT NULL,
  `penulis` varchar(100) DEFAULT NULL,
  `tahun` varchar(5) DEFAULT NULL,
  `judul` varchar(100) DEFAULT NULL,
  `kota` varchar(30) DEFAULT NULL,
  `penerbit` varchar(100) DEFAULT NULL,
  `kategori` varchar(20) DEFAULT NULL,
  `jumlah` int(5) DEFAULT NULL,
  `gambar` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `daftar_kunjungan`
--
ALTER TABLE `daftar_kunjungan`
  ADD PRIMARY KEY (`tgl_input`);

--
-- Indexes for table `pinjaman_barang`
--
ALTER TABLE `pinjaman_barang`
  ADD PRIMARY KEY (`tgl_input`);

--
-- Indexes for table `pinjaman_buku`
--
ALTER TABLE `pinjaman_buku`
  ADD PRIMARY KEY (`tgl_input`);

--
-- Indexes for table `stok_barang`
--
ALTER TABLE `stok_barang`
  ADD PRIMARY KEY (`kode_barang`);

--
-- Indexes for table `stok_buku`
--
ALTER TABLE `stok_buku`
  ADD PRIMARY KEY (`kode_buku`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
