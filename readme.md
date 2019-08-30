<h1>This repo contains <u>8085 emulator</u></h1>
<ul>
<li>this 8085 emulator emulates almost all instructions accept few (see <i>TODO</i>.</li>
<li>it does not emulates at same speed though (3.2MHz) but at speed of host system.</li>
<li>it comes with 64K ram.</li>
</ul>
<h5>How to use?</h5>
<ul>
<li>
write your assembly code as usual with few things in mind.</li>
<li><pre>
// this is 8085 assembly program to sort numbers
LXI H,3000
MVI M,6
LXI H,3001
MVI M,1
LXI H,3002
MVI M,5
LXI H,3003
MVI M,2
LXI H,3004
MVI M,4
LXI H,3005
MVI M,3
hlt
// this is comment
%execute
%getmemory 3000 3010
MVI B,5
START:
LXI H,3000
MVI C,5
BACK:
MOV A,M
INX H
CMP M
JC SKIP
JZ SKIP
MOV D,M
MOV M,A
DCX H
MOV M,D
INX H
SKIP:
DCR C
JNZ BACK
DCR B
JNZ START
HLT
%execute
%getregister
%getmemory 3000 3010
q
</pre></li>
<li><code>//</code> at beginning of line indicate comment.</li>
<li><code>label:</code> should not follow any instruction but newline.</li>
<li><code>MOV M,A</code> this type to instruction showld not contain space between comma(,) and oprands.</li>
<li><code>%</code> sign following command is used to tell emulator what to do.</li>
<li>every file must end with <code>q</code> to mark end.</li>
</ul>

<h5><code>%instructions</code>:</h5>
<ul>
<li><code>%execute</code> executes instructions above can be called multiple times.</li>
<li><code>%getregister</code>prints register contents.</li>
<li><code>%getmemory <i> start end</i></code> returns memory content in range.</li>
<li><code>%setpc <i>location</i></code> set position of PC registor from here execution starts.</li>
<li><code>%setmemory <i>location</i></code> set position of memory where instruction is to be stored.</li>
<li><code>%initmemory</code> set memory content to 0.</li>
</ul>

<h5>NOTE:</h5>
<ul>
<li>default <code>PC=0</code> <code>start memory=0</code> <code>SP=65520</code></li>
<li>every registor and memory is 0 initially.</li>
<li>before execution <code>HLT</code> must be there else there uill be infinite loop.</li>
</ul>

<h5>OUTPUT:</h5>
<pre>
######################
# Address  #  Value  #
######################
#   3000  #     6    #
#   3001  #     1    #
#   3002  #     5    #
#   3003  #     2    #
#   3004  #     4    #
#   3005  #     3    #
#   3006  #     0    #
#   3007  #     0    #
#   3008  #     0    #
#   3009  #     0    #
#   3010  #     0    #
######################
########################
# Register  #  values  #
########################
#    A      #     5    #
#    B      #     0    #
#    C      #     0    #
#    D      #     3    #
#    E      #     0    #
#    H      #    11    #
#    L      #   189    #
#    SP     # 65520    #
#    IR     #   118    #
#    PC     #    61    #
#    S      #     0    #
#    Z      #     1    #
#    AC     #     0    #
#    P      #     1    #
#    CY     #     0    #
#    BC     #     0    #
#    DE     #   768    #
#    HL     #  3005    #
########################
######################
# Address  #  Value  #
######################
#   3000  #     1    #
#   3001  #     2    #
#   3002  #     3    #
#   3003  #     4    #
#   3004  #     5    #
#   3005  #     6    #
#   3006  #     0    #
#   3007  #     0    #
#   3008  #     0    #
#   3009  #     0    #
#   3010  #     0    #
######################
</pre>

<h5>How to execute file?</h5>
<ul>
<li>using commandline <code>java -Dfile.encoding=UTf-8 -jar 8085-emulator.jar in.file
</code></li>
<li>run JAR directly and enter in prompt
<code>java -Dfile.encoding=UTf-8 -jar 8085-emulator.jar</code> > 
<code>Enter file name to execute: in.file
</code>
</li>
</ul>

<h5><i>References</i><h5>
<p><i>
<a href="https://www.jameco.com/Jameco/Products/ProdDS/52062.pdf">https://www.jameco.com/Jameco/Products/ProdDS/52062.pdf</a>
<a href="http://www.eazynotes.com/notes/microprocessor/notes/opcodes-table-of-intel-8085.pdf">http://www.eazynotes.com/notes/microprocessor/notes/opcodes-table-of-intel-8085.pdf</a>
<a href="https://www.tutorialspoint.com/microprocessor/microprocessor_8085_architecture.htm">https://www.tutorialspoint.com/microprocessor/microprocessor_8085_architecture.htm</a>
<a href="https://www.geeksforgeeks.org/microprocessor-tutorials/">https://www.geeksforgeeks.org/microprocessor-tutorials/</a> (8085)
</i>
</p>