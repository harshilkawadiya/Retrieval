#!/usr/bin/perl -w
use Cwd;
$ROUGE="./ROUGE-1.5.5.pl";
$cmd="$ROUGE -e data -n 1 -a -z SIMPLE my-test1.txt 26";
print $cmd,"\n";
system($cmd);
