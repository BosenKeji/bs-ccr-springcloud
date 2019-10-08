local number=ARGV[1]
local result={}
for i=1,#(KEYS) do
    result[i]=redis.call('ZINCRBY',ARGV[2],number,KEYS[i])
end
return result