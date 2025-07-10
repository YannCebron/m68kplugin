These registers receive the DMA data fetched from RAM by the
bit plane address pointers described above.
They may also be rewritten by either micro.
They act as a 8 word parallel to serial buffer for up
to 8 memory 'bit planes'. x=1-8 the parallel to serial
conversion id triggered whenever bit plane #1 is
written, inducing the completion of all bit planes for
that word (16/32/64 pixels). The MSB is output first,
and is therefore always on the left.